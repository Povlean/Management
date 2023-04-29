package com.ean.gundam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.gundam.commons.Result;
import com.ean.gundam.model.domain.User;
import com.ean.gundam.model.request.LoginRequest;
import com.ean.gundam.model.request.RegisterRequest;

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
}
