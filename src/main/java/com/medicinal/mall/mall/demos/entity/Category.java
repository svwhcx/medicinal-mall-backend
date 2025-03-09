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
 * @Date 2025/3/2 17:09
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("category")
public class Category {

    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 分类的排序
     */
    @TableField(value = "`sort`")
    private Integer sort;

    /**
     * 分类的描述
     */
    private String description;
}
