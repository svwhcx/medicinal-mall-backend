package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/2 23:58
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteVo {


    /**
     * 封面
     */
    private String img;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 收藏的id
     */
    private Integer id;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 用户收藏的对应的商品的id
     */
    private Integer productId;
}
