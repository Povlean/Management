package com.ean.gundam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ean.gundam.commons.Result;
import com.ean.gundam.constants.ResCode;
import com.ean.gundam.model.domain.User;
import com.ean.gundam.model.request.LoginRequest;
import com.ean.gundam.model.request.RegisterRequest;
import com.ean.gundam.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:TODO
 * @author:Povlean
 */
@RestController
// 可以用，但是这里使用的是拦截器处理跨域问题
// @CrossOrigin(value = {"http://localhost:8888/","http://192.168.31.88:8888/"})
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String,Object>> userLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        if (ObjectUtil.isNull(loginRequest)) {
            return Result.error(ResCode.ERROR);
        }
        return userService.userLogin(loginRequest,httpServletRequest);
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token) {
        return userService.getUserInfo(token);
    }

    @PostMapping("/register")
    public Result userRegister(@RequestBody RegisterRequest registerReq) {
        if (ObjectUtil.isNull(registerReq)) {
            return Result.error(ResCode.ERROR);
        }
        return userService.userRegister(registerReq);
    }

    @PostMapping("/logout")
    public Result userLogout (@RequestHeader("X-Token") String token) {
        if (token == null) {
            return Result.error();
        }
        return userService.logout(token);
    }

    /**
    * @description:TODO
    * @author:Povlean
    * @date:2023/4/22 21:27
    * @param:* @param null
    * @return:
    */
    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                              @RequestParam(value = "phone",required = false) String phone,
                              @RequestParam("pageNo") Long pageNo,
                              @RequestParam("pageSize") Long pageSize) {
        return userService.getUserList(username,phone,pageNo,pageSize);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        if (ObjectUtil.isNull(user)) {
            return Result.error("添加信息为空");
        }
        return userService.addUser(user);
    }

    /**
    * @description:数据回显
    * @author:Povlean
    * @date:2023/4/30 9:48
    * @param:* @param id
    * @return:* @return Result
    */
    @GetMapping("/get/{id}")
    public Result getUserById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return Result.error(ResCode.ERROR);
        }
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        if (ObjectUtil.isNull(user)) {
            return Result.error("更新信息为空");
        }
        return userService.updateUser(user);
    }

}