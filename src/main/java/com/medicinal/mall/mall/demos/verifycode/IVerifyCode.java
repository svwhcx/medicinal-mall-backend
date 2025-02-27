package com.medicinal.mall.mall.demos.verifycode;

import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;

/**
 * @description 这里是验证码服务
 * @Author cxk
 * @Date 2025/2/27 9:26
 */
public interface IVerifyCode {

    /**
     * 发送验证码操作。
     * @param verifyCodeRequest 发送验证码请求
     */
    void sendVerifyCode(VerifyCodeRequest verifyCodeRequest);

    /**
     * 校验校验码是否正确
     * @param verifyCodeRequest 校验验证码请求
     * @return 验证码是否正确
     */
    Boolean checkVerifyCode(VerifyCodeRequest verifyCodeRequest);

    /**
     * 标记该验证码已被使用
     * @param verifyCodeRequest 验证码信息
     */
    void verifyCodeUsed(VerifyCodeRequest verifyCodeRequest);
}
