package com.ean.gundam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.gundam.commons.Result;
import com.ean.gundam.constants.CommonConstant;
import com.ean.gundam.constants.ResCode;
import com.ean.gundam.mapper.UserMapper;
import com.ean.gundam.model.domain.User;
import com.ean.gundam.model.request.LoginRequest;
import com.ean.gundam.model.request.RegisterRequest;
import com.ean.gundam.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author Asphyxia
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2023-04-22 11:17:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public Result<Map<String,Object>> userLogin(LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        // 密码和用户名都不为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.error(ResCode.ERROR,"用户名或密码不能为空");
        }
        User user = userMapper.userLogin(username, password);
        if (user == null) {
            return Result.error(ResCode.ERROR,"用户不存在");
        }
        // 结果不为空，则生成token，将用户信息存入redis
        // 舍弃用户信息存入session的方案
        // 暂时用UUID，最终方案用的是jwt
        String key = "user:" + UUID.randomUUID();
        // 存入redis
        user.setPassword(null);
        redisTemplate.opsForValue().set(key,user,30, TimeUnit.MINUTES);
        // 返回数据处理
        Map<String, Object> data = new HashMap<>();
        data.put("token",key);
        return Result.success(data,"登录成功");
    }



    @Override
    public Result userRegister(RegisterRequest registerReq) {
        String username = registerReq.getUsername();
        String checkPwd = registerReq.getCheckPwd();
        String password = registerReq.getPassword();
        // 账号或密码为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(checkPwd)) {
            return Result.error(ResCode.ERROR,"账号密码格式错误");
        }
        // 如果密码和确认密码的长度一样
        if (checkPwd.length() != password.length() || !checkPwd.equals(password)) {
            return Result.error(ResCode.ERROR,"账号密码格式错误");
        }
        // 校验密码长度
        if (password.length() < 6) {
            return Result.error(ResCode.ERROR,"账号密码格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUsername,username);
        User sameUser = this.getOne(queryWrapper);
        // 数据库中已有该用户
        if (sameUser != null) {
            return Result.error(ResCode.ERROR,"该用户已存在");
        }
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        boolean save = this.save(user);
        // 如果save失败则返回
        if (!save) {
            return Result.error(ResCode.ERROR,"注册失败");
        }
        // 返回结果
        return new Result(ResCode.SUCCESS,"注册成功",user);
    }


    @Override
    public Result getAllUser() {
        List<User> users = userMapper.getAllUser();
        users = users.stream().map((user) -> {
            user = getSafetyUser(user);
            return user;
        }).collect(Collectors.toList());
        return new Result(ResCode.SUCCESS,"查询成功",users);
    }

    @Override
    public Result<Map<String, Object>> getUserInfo(String token) {
        // 从redis中取出该数据
        Object obj = redisTemplate.opsForValue().get(token);
        if (ObjectUtil.isNull(obj)) {
            return Result.error(ResCode.ERROR,"该用户不存在");
        }
        // 将该数据转换为User对象
        // 先将obj转换为json字符串，然后json字符串转换为
        User user = JSON.parseObject(JSON.toJSONString(obj), User.class);
        if (user == null) {
            return Result.error(ResCode.ERROR,"该用户不存在");
        }
        // 返回类
        Map<String,Object> data = new HashMap<>();
        // name和avatar是从redis中获取的
        data.put("name",user.getUsername());
        data.put("avatar",user.getAvatar());
        // roles是从数据库中查表查出来的
        List<String> roles = this.getBaseMapper().getRolesByUserId(user.getId());
        data.put("roles",roles);
        return Result.success(data);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete(token);
        return Result.success("登出成功");
    }

    @Override
    public Result<Map<String, Object>> getUserList(String username, String phone, Long pageNo, Long pageSize) {
        // 校验数据
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(org.springframework.util.StringUtils.hasLength(username),User::getUsername, username);
        queryWrapper.eq(org.springframework.util.StringUtils.hasLength(phone),User::getPhone, phone);
        // 构造分页
        Page page = new Page(pageNo,pageSize);
        this.page(page,queryWrapper);
        // 返回结果
        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @Override
    public Result addUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.error("必填信息为空");
        }
        if (StringUtils.isBlank(email)) {
            return Result.error("必填信息为空");
        }
        // 表单信息正常
        boolean save = this.save(user);
        if (!save) {
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    public User getSafetyUser(User user) {
        User safetyUser = new User();
        safetyUser.setUsername(user.getUsername());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setStatus(user.getStatus());
        safetyUser.setAvatar(user.getAvatar());
        return safetyUser;
    }

}




