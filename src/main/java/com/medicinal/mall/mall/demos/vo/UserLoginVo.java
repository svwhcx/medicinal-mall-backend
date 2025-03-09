package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.DocFlavor;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:21
 */
@Getter
@Setter
public class UserLoginVo {

    // 用户名称
    private UserInfoVo userInfo;

    // 用户登录成功后的token信息
    private String token;

    // 用户的id（可以不需要了）
//    private Integer userId;
}


