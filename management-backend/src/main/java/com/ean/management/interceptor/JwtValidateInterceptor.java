package com.ean.management.interceptor;

import com.alibaba.fastjson.JSON;
import com.ean.management.commons.Result;
import com.ean.management.constants.ResCode;
import com.ean.management.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:TODO
 * @author:Povlean
 */
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证");
        if (token == null) {
            log.debug(request.getRequestURI() + "验证为空");
            return false;
        }
        // 当token不为空时，校验该token是否能够进行转换
        try {
            jwtUtil.parseToken(token);
            log.debug(request.getRequestURI() + "验证通过");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug(request.getRequestURI() + "验证失败");
        // 向前端返回json字符串
        Result<Object> error = Result.error(ResCode.TOKEN_EXPIRE, "token失效，请重新登录");
        response.getWriter().write(JSON.toJSONString(error));
        return false;
    }
}