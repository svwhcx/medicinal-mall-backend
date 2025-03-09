package com.medicinal.mall.mall.demos.verifycode;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.util.KaptchaUtils;
import com.medicinal.mall.mall.demos.verifycode.cache.IVerifyCodeCache;
import com.medicinal.mall.mall.demos.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description 图片验证码的生成机制等等
 * @Author cxk
 * @Date 2025/3/8 10:06
 */
@Component("photoVerifyCode")
public class PhotoVerifyCode implements IVerifyCode {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private IVerifyCodeCache iVerifyCodeCache;

   /* @Override
    public void sendVerifyCode(VerifyCodeRequest verifyCodeRequest) {
        generateVerifyCode(verifyCodeRequest);
    }
*/
    @Override
    public Boolean checkVerifyCode(VerifyCodeRequest verifyCodeRequest) {
        boolean res =  iVerifyCodeCache.checkCode(verifyCodeRequest);
        iVerifyCodeCache.setCodeUsed(verifyCodeRequest);
        return res;
    }

    @Override
    public void verifyCodeUsed(VerifyCodeRequest verifyCodeRequest) {

    }

    @Override
    public VerifyCodeVo generateVerifyCode(VerifyCodeRequest verifyCodeRequest) throws Exception {

        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();

        // 生成验证码文字
        String verifyCode = defaultKaptcha.createText();
        System.out.println("当前验证码的文字为：："+verifyCode);
        // 生成一串UUID
        String codeKey = UUID.randomUUID().toString();
        BufferedImage challenge = defaultKaptcha.createImage(verifyCode);
        ImageIO.write(challenge, "jpg", imgOutputStream);
        // 将图片转为base64存储
        String imgBase64 = Base64.getEncoder().encodeToString(imgOutputStream.toByteArray());
        // 将图片验证码的UUID和对应的verifyCode放入验证码缓存器中。
        verifyCodeRequest.setAddr(codeKey);
        verifyCodeRequest.setCode(verifyCode);
        iVerifyCodeCache.saveCode(verifyCodeRequest);
        VerifyCodeVo verifyCodeVo = new VerifyCodeVo();
        verifyCodeVo.setCode(codeKey);
        verifyCodeVo.setImgUrl("data:image/png;base64,"+imgBase64);
        return verifyCodeVo;
    }
}
