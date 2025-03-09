package com.medicinal.mall.mall.demos.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/1 21:12
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordCmd {

    /**
     * 用户的旧密码
     */
    private String oldPassword;

    /**
     * 用户的新密码
     */
    private String newPassword;
}
