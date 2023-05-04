package com.ean.management;

import com.ean.management.model.domain.User;
import com.ean.management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @description:TODO
 * @author:Povlean
 */
@SpringBootTest
public class LoginTest {

    @Resource
    private UserService userService;

    @Test
    public void test1() {
        User user = new User();
        user.setPassword("123456");
        // md5:b3bd460a7dc3c3a4c3b71eadc465aad0
        user.setUsername("newDemo");
        userService.addUser(user);
    }
}
