package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/27 10:00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistryRequest {

    // 用户名
    private String username;

    // 密码
    private String password;

    // 邮箱
    private String email;

    // 手机号
    private String phoneNumber;

    // 验证码
    private String verifyCode;
}
