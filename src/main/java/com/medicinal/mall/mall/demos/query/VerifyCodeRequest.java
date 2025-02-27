package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 验证码请求的一些东西
 * @Author cxk
 * @Date 2025/2/27 9:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeRequest {

    // 发送验证码的目标地址（可以是邮箱、可以是手机号、可以是其他的第三方服务等等）
    private String addr;

    // 验证码（验证时需要）
    private String code;

    // 验证码类型（1：邮箱验证码，2：手机验证码）
    private Integer type;

}
