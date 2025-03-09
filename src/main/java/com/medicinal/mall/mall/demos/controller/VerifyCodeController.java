package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.query.VerifyCodeRequest;
import com.medicinal.mall.mall.demos.verifycode.IVerifyCode;
import com.medicinal.mall.mall.demos.verifycode.VerifyCodeConstant;
import com.medicinal.mall.mall.demos.verifycode.context.VerifyCodeContext;
import com.medicinal.mall.mall.demos.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/28 18:08
 */
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController extends BaseController {

    @Autowired
    private VerifyCodeContext verifyCodeContext;

    /**
     * 发送验证码操作
     *
     * @param verifyCodeRequest 验证码的一些东西
     * @return
     */
    @PostMapping("/send")
    public ResultVO<Void> sendVerifyCode(VerifyCodeRequest verifyCodeRequest) throws Exception {
        verifyCodeContext.getIVerifyCode(verifyCodeRequest).generateVerifyCode(verifyCodeRequest);
        return success();
    }

    /**
     * 获取一个图片的验证码数据
     *
     * @return
     */
    @GetMapping("/photo")
    public ResultVO<VerifyCodeVo> generatePictureVerifyCode() throws Exception {
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest();
        verifyCodeRequest.setType(VerifyCodeConstant.PHOTO_VERIFY);
        return success(this.verifyCodeContext.getIVerifyCode(verifyCodeRequest).generateVerifyCode(verifyCodeRequest));
    }
}
