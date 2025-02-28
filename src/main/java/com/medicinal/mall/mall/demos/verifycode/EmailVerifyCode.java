package com.medicinal.mall.mall.demos.verifycode;

import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.svwh.mailservice.core.MailService;
import com.svwh.mailservice.mail.HtmlMail;
import com.svwh.mailservice.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @description
 * @Author cxk
 * @Date 2025/2/27 9:30
 */
@Component
public class EmailVerifyCode implements IVerifyCode{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerifyCode.class);

    @Autowired
    private MailService mailService;

    // 直接在这里进行模拟验证码池
    private final ConcurrentHashMap<String,Code> CODE_POOL = new ConcurrentHashMap<>();

    // 验证码有效时长
    private static final int EXPIRE_TIME = 30 * 60 * 1000;

    @Override
    public void sendVerifyCode(VerifyCodeRequest verifyCodeRequest) {
        // 先进行随机生成,验证码一般30分钟内有效
        String verifyCode =String.valueOf(ThreadLocalRandom.current().nextInt(100000,999999));
        CODE_POOL.put(verifyCodeRequest.getAddr(),new Code(verifyCode,System.currentTimeMillis()+EXPIRE_TIME));
        // 接下来就是调用邮件服务，发送一个验证码的邮箱
        Mail mail = new HtmlMail();
        List<String> mailList = new ArrayList<>();
        mailList.add(verifyCodeRequest.getAddr());
        mail.setToMail(mailList);
        mail.setContent(verifyCodeRequest.getMsg() + ",您的验证码为:" + verifyCode);
        mail.setSubject("全球药材在线商城");
        mailService.send(mail);
        mail.setToMail(mailList);
        LOGGER.info("邮件验证码为  》》》 目的邮箱：{} ，验证码：{}  有效时间 30 分钟",
                verifyCodeRequest.getAddr(), verifyCode);
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
