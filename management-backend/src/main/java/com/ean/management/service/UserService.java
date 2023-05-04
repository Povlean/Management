package com.ean.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.management.commons.Result;
import com.ean.management.model.domain.User;
import com.ean.management.model.request.LoginRequest;
import com.ean.management.model.request.RegisterRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
* @author Asphyxia
* @description 针对表【tb_user】的数据库操作Service
* @createDate 2023-04-22 11:17:06
*/
public interface UserService extends IService<User> {

    Result<Map<String,Object>> userLogin(LoginRequest loginRequest, HttpServletRequest httpServletRequest);

    Result userRegister(RegisterRequest registerReq);

    Result getAllUser();

    Result<Map<String, Object>> getUserInfo(String token);

    Result logout(String token);

    Result<Map<String, Object>> getUserList(String username, String phone, Long pageNo, Long pageSize);

    Result addUser(User user);

    Result getUserById(Integer id);

    Result updateUser(User user);

    Result deleteUserById(Integer id);
}
