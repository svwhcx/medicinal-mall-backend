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
 * @Date 2025/2/24 10:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TableName("user_addr")
public class UserAddr {

    @TableId
    private Integer id;

    // 详细的地址位置
    private String addr;

    // 关联的用户的id
    private Integer userId;

    // 收件人的名称（TODO 注意这里要进行用户的敏感信息的过滤）
    private String recipient;

    // 收件人的联系方式：目前就是邮箱、或者时手机号之类的(TODO 这里也要进行用户的敏感数据的一个脱敏操作）
    private String contactDetail;
}
