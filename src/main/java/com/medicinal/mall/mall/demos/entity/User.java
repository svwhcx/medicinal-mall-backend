package com.medicinal.mall.mall.demos.entity;

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
 * @Date 2025/2/23 21:32
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId
    // 数据库的主键id
    private Integer id;

    // 用户登录的用户名
    private String account;

    // 用户的昵称
    private String nickname;

    // 用户的手机号信息，或者联系方式
    private String phoneNumber;

    // 用户的自我介绍或者说简介
    private String introduction;

    // 用户的头像。默认为空、或者说默认就是系统提供的默认头像。
    private String avatar;

    // 用户登录的密码
    private String password;

    // 用户的性别
    private Integer sex;

    // 当前账号的状态
    private Integer status;

    // 用户默认的收获地址的ID
    private Integer mainAddrId;

    // 用户绑定的邮箱地址
    private String email;


    // 用户的注册时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
