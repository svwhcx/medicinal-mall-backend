package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/18 22:27
 */
@Setter
@Getter
@TableName("supplier")
public class Supplier {


    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系手机号
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 地址
     */
    private String address;

    /**
     * 描述信息
     */
    private String description;


    /**
     * 状态：是否启用
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Boolean isDelete;
}
