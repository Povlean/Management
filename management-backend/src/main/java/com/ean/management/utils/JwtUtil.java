package com.ean.management.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @description:TODO
 * @author:Povlean
 */
@Component
public class JwtUtil {
    // 有效期
    private static final long JWT_EXPIRE = 30 * 60 * 1000l;
    // 令牌密钥
    private static final String JWT_KEY = "123456";

    public String createToken(Object data) {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expireTime = currentTime + JWT_EXPIRE;
        // 构建JWT
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID() + "")
                .setSubject(JSON.toJSONString(data))
                .setIssuer("system")
                .setIssuedAt(new Date(currentTime))
                .signWith(SignatureAlgorithm.HS256, encodeSecret(JWT_KEY))
                .setExpiration(new Date(expireTime));
        return builder.compact();
    }

    private SecretKey encodeSecret(String jwtKey) {
        byte[] encode = Base64.getEncoder().encode(jwtKey.getBytes());
        SecretKeySpec aes = new SecretKeySpec(encode, 0, encode.length, "AES");
        return aes;
    }

    public Claims parseToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    public <T> T parseToken(String token,Class<T> clazz) {
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return JSON.parseObject(body.getSubject(),clazz);
    }
}
