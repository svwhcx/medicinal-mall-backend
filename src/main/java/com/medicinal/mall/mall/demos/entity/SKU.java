package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/4 0:06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("sku")
public class SKU {


    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * SKU的唯一code编码
     */
    private String skuCode;

    /**
     * 当前商品的价格
     */
    private BigDecimal price;

    /**
     * 当前商品剩余库存
     */
    private Integer stock;

    /**
     * SKU对应的图片
     */
    private String image;

    /**
     * 重量
     */
    private String weight;

    /**
     * 包装类型
     */
    private String packaging;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * SKU 对应的规格信息
     */
    private String specs;
}
