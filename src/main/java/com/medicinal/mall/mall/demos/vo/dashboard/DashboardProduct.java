package com.medicinal.mall.mall.demos.vo.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/7 15:15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProduct {



    private Integer productId;

    /**
     * 商品的名称
     */
    private String name;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品的销量
     */
    private Integer saleNum;

    /**
     * 库存
     */
    private Integer stock;

}
