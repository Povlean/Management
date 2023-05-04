package com.ean.management;

import com.ean.management.model.domain.User;
import com.ean.management.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @description:TODO
 * @author:Povlean
 */
@SpringBootTest
public class JwtTest {
    @Resource
    private JwtUtil jwtUtil;

    @Test
    public void test1() {
        User user = new User();
        user.setUsername("182");
        user.setPassword("182465");
        user.setEmail("acwing918@foxmail.com");
        String token = jwtUtil.createToken(user);
        System.out.println(token);
    }

    // eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3OTQ1OTJmMi0wOTdlLTQ3ZTUtOTU2NS1kNjI3MTY1MmJjNDQiLCJzdWIiOiJ7XCJlbWFpbFwiOlwiYWN3aW5nOTE4QGZveG1haWwuY29tXCIsXCJwYXNzd29yZFwiOlwiMTgyNDY1XCIsXCJ1c2VybmFtZVwiOlwiMTgyXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjgyOTk1MDIyLCJleHAiOjE2ODI5OTY4MjJ9.7lad1YZHPb2694-7ioYRrUU5srf5-FYkMDc_1UCbi4k
    @Test
    public void test2() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3OTQ1OTJmMi0wOTdlLTQ3ZTUtOTU2NS1kNjI3MTY1MmJjNDQiLCJzdWIiOiJ7XCJlbWFpbFwiOlwiYWN3aW5nOTE4QGZveG1haWwuY29tXCIsXCJwYXNzd29yZFwiOlwiMTgyNDY1XCIsXCJ1c2VybmFtZVwiOlwiMTgyXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjgyOTk1MDIyLCJleHAiOjE2ODI5OTY4MjJ9.7lad1YZHPb2694-7ioYRrUU5srf5-FYkMDc_1UCbi4k".trim();
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
