package com.medicinal.mall.mall.demos.verifycode;

import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.verifycode.cache.IVerifyCodeCache;
import com.medicinal.mall.mall.demos.vo.VerifyCodeVo;
import com.svwh.mailservice.core.MailService;
import com.svwh.mailservice.mail.HtmlMail;
import com.svwh.mailservice.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @description
 * @Author cxk
 * @Date 2025/2/27 9:30
 */
@Component("emailVerifyCode")
public class EmailVerifyCode implements IVerifyCode{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerifyCode.class);

    @Autowired
    private MailService mailService;

    // 直接在这里进行模拟验证码池

    @Autowired
    private IVerifyCodeCache verifyCodeCache;

//    @Override
//    public void sendVerifyCode(VerifyCodeRequest verifyCodeRequest) {
//        // 先进行随机生成,验证码一般30分钟内有效
//        String verifyCode =String.valueOf(ThreadLocalRandom.current().nextInt(100000,999999));
//        // 接下来就是调用邮件服务，发送一个验证码的邮箱
//        verifyCodeRequest.setCode(verifyCode);
//        verifyCodeCache.saveCode(verifyCodeRequest);
//        Mail mail = new HtmlMail();
//        List<String> mailList = new ArrayList<>();
//        mailList.add(verifyCodeRequest.getAddr());
//        mail.setToMail(mailList);
//        mail.setContent(verifyCodeRequest.getMsg() + ",您的验证码为:" + verifyCode);
//        mail.setSubject("全球药材在线商城");
//        mailService.send(mail);
//        mail.setToMail(mailList);
//        LOGGER.info("邮件验证码为  》》》 目的邮箱：{} ，验证码：{}  有效时间 30 分钟",
//                verifyCodeRequest.getAddr(), verifyCode);
//    }

    @Override
    public Boolean checkVerifyCode(VerifyCodeRequest verifyCodeRequest) {
        // 校验验证码是否正确
        return verifyCodeCache.checkCode(verifyCodeRequest);
    }

    @Override
    public void verifyCodeUsed(VerifyCodeRequest verifyCodeRequest) {
        // 这里不能单纯地进行验证码的删除，而是要进行校验
        verifyCodeCache.setCodeUsed(verifyCodeRequest);
    }

    @Override
    public VerifyCodeVo generateVerifyCode(VerifyCodeRequest verifyCodeRequest) throws Exception {
        // 先进行随机生成,验证码一般30分钟内有效
        String verifyCode =String.valueOf(ThreadLocalRandom.current().nextInt(100000,999999));
        // 接下来就是调用邮件服务，发送一个验证码的邮箱
        verifyCodeRequest.setCode(verifyCode);
        verifyCodeCache.saveCode(verifyCodeRequest);
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
        return null;
    }


}
