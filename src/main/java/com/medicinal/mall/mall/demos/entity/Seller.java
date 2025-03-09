package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    /**
     * 用户的头像（这里是商家）
     */
    private String avatar;

    // 手机号
    private String phoneNumber;

    // 用户名
    private String username;

    // 密码
    @TableField("`password`")
    private String password;


    // 商家的店铺介绍，简介等等
    @TableField("`desc`")
    private String desc;

    // 商家对应的店铺的封面图片
    private Integer coverPhotoId;

    // 商家的邮箱
    private String email;

    // 用户的注册/创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
