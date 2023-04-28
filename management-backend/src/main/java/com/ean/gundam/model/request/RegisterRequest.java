package com.ean.gundam.model.request;

import lombok.Data;

/**
 * @description:TODO
 * @author:Povlean
 */
@Data
public class RegisterRequest {
    private String username;

    private String password;

    private String checkPwd;
}
