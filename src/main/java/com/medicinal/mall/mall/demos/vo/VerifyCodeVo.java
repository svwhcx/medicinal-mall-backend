package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 用于验证码的数据vo<p>
 *     这里主要包括图片验证码对应的code编码，前端验证的时候需要带上这个code编码值
 * @Author cxk
 * @Date 2025/3/8 9:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeVo {

    /**
     * 图片验证码对应的编码
     */
    String code;

    /**
     * base64 图片地址，前端直接用base64访问图片
     */
    private String imgUrl;
}
