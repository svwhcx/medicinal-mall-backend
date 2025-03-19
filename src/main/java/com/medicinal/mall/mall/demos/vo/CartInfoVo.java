package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/2 14:39
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartInfoVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 药材名称
     */
    private String name;
    /**
     * 封面图
     */
    private String image;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 最开始的价格
     */
    private String originalPrice;
    /**
     * 单价
     */
    private String unit;
    /**
     * 库存
     */
    private Integer stock;

    private Integer skuId;
}
