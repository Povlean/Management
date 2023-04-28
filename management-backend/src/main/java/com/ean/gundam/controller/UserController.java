package com.ean.gundam.controller;

import com.ean.gundam.commons.Result;
import com.ean.gundam.constants.ResCode;
import com.ean.gundam.model.domain.User;
import com.ean.gundam.model.request.LoginRequest;
import com.ean.gundam.model.request.RegisterRequest;
import com.ean.gundam.service.UserService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/register")
    public Result userRegister(@RequestBody RegisterRequest registerReq) {
        if (ObjectUtil.isNull(registerReq)) {
            return Result.error(ResCode.ERROR);
        }
        return userService.userRegister(registerReq);
    }

    @PostMapping("/logout")
    public Result userLogout (HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return Result.error(ResCode.ERROR);
        }
        return userService.logout(httpServletRequest);
    }

    /**
    * @description:TODO
    * @author:Povlean
    * @date:2023/4/22 21:27
    * @param:* @param null
    * @return:
    */
    @GetMapping("/list")
    public Result getAllUser() {
        return userService.getAllUser();
    }

}
