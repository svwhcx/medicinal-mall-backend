package com.medicinal.mall.mall.demos.verifycode;

import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/27 9:30
 */
@Component
public class EmailVerifyCode implements IVerifyCode{

    // 直接在这里进行模拟验证码池
    private final ConcurrentHashMap<String,Code> CODE_POOL = new ConcurrentHashMap<>();

    // 验证码有效时长
    private static final int EXPIRE_TIME = 30 * 60 * 1000;

    @Override
    public void sendVerifyCode(VerifyCodeRequest verifyCodeRequest) {
        // 先进行随机生成,验证码一般30分钟内有效
        String verifyCode =String.valueOf(ThreadLocalRandom.current().nextInt(100000,999999));
        CODE_POOL.put(verifyCodeRequest.getAddr(),new Code(verifyCode,System.currentTimeMillis()+EXPIRE_TIME));
    }

    @Override
    public Boolean checkVerifyCode(VerifyCodeRequest verifyCodeRequest) {
        // 校验验证码是否正确
        Code code = CODE_POOL.get(verifyCodeRequest.getAddr());
        if (code == null || System.currentTimeMillis() > code.getExpireTime()){
            return false;
        }
        return true;
    }

    @Override
    public void verifyCodeUsed(VerifyCodeRequest verifyCodeRequest) {
        // 这里不能单纯地进行验证码的删除，而是要进行校验
        Code code = CODE_POOL.get(verifyCodeRequest.getAddr());
        if (code != null && code.getCode().equals(verifyCodeRequest.getCode())){
            CODE_POOL.remove(verifyCodeRequest.getAddr());
        }
    }


    private class Code{

        private String code;
        private Long expireTime;

        public Code(String code, Long expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Long expireTime) {
            this.expireTime = expireTime;
        }
    }
}
