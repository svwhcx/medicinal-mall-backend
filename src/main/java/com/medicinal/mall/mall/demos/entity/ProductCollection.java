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
 * @Date 2025/2/24 10:08
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("product_collection")
public class ProductCollection {

    // 主键id
    @TableId
    private Integer id;

    // 药材商品的id
    private Integer  productId;

    // 用户iD
    private Integer userId;

    // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    // 是否删除
    private Boolean isDelete;

    // 删除时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;



}
