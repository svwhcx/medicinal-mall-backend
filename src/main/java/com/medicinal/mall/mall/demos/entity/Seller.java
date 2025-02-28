package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("seller")
public class Seller {


    // 主键id
    @TableId
    private Integer id;

    // 商家的地址
    private String addr;

    // 手机号
    private String phoneNumber;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 商家的店铺介绍，简介等等
    private String desc;

    // 商家对应的店铺的封面图片
    private Integer coverPhotoId;

    // 商家的邮箱
    private String email;
}
