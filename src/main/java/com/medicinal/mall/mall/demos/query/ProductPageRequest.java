package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 药材商品信息的分页查询了已经是
 * @Author cxk
 * @Date 2025/2/27 17:46
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageRequest extends PageQuery{

    // 价格过滤之类的
    private String price;

    // 商品最低的价格
    private String minPrice;

    // 商品最搞的价格
    private String maxPrice;

    // 商品的名称
    // TODO 这里应该是使用模糊查询的。
    private String name;

    // 药材的类型。
    private Integer type = 0;

    /**
     * 当前商品的状态<p>
     * 0: 未发布<p>
     * 1： 已发布<p>
     */
    private Integer status;

    /**
     * 商品种类的id
     */
    private Integer categoryId;
}
