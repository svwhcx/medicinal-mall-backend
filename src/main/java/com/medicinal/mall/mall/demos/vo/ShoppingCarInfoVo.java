package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 购物车信息（前端需要展示的)
 * @Author cxk
 * @Date 2025/2/27 14:40
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCarInfoVo {

    // 主键id
    private Integer id;

    // 药材商品的ID
    private Integer medicinalId;

    // 这个是商品的名称
    private String medicinalName;

    // 该购物车商品的总的价格
    private String price;

    // 预购买的数量
    private Integer preBuyNum;
}
