package com.medicinal.mall.mall.demos.verifycode.context;

import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.verifycode.EmailVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.IVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.PhotoVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.VerifyCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 负责验证码系统上下文的获取操作。
 * @Author cxk
 * @Date 2025/3/8 10:06
 */
@Component
public class VerifyCodeContext {

    private final Map<Integer, IVerifyCode> iVerifyCodeMap = new ConcurrentHashMap<>();

    @Autowired
    public VerifyCodeContext(EmailVerifyCode emailVerifyCode, PhotoVerifyCode photoVerifyCode){
        iVerifyCodeMap.put(VerifyCodeConstant.PHOTO_VERIFY,photoVerifyCode);
        iVerifyCodeMap.put(VerifyCodeConstant.EMAIL_VERIFY,emailVerifyCode);
    }

    /**
     * 根据类型获取对应的验证码服务。
     * @param verifyType 验证码类型
     * @return
     */
    public IVerifyCode getIVerifyCode(Integer verifyType){
        return iVerifyCodeMap.get(verifyType);
    }


    /**
     * 根据类型获取对应的验证码服务
     * @param verifyCodeRequest 包含验证码类型的验证码请求数据。
     * @return
     */
    public IVerifyCode getIVerifyCode(VerifyCodeRequest verifyCodeRequest){
        return iVerifyCodeMap.get(verifyCodeRequest.getType());
    }

}
