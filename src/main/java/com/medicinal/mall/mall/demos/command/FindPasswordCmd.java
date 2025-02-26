package com.medicinal.mall.mall.demos.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 找回密码的操作
 * @Author cxk
 * @Date 2025/2/26 21:14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordCmd {

    // 用户标志：（商家还是说是买家）
    private Integer flag;

    // 用户注册时绑定的邮箱.
    private String email;

    // 找回密码时的邮箱验证码
    private String code;

    // 要设置的新密码
    private String newPwd;

}
