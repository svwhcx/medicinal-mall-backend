package com.medicinal.mall.mall.demos.query;

import com.medicinal.mall.mall.demos.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 10:17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {


    /**
     * 当前批次的订单中的单独的商品订单信息
     */
    private List<Order> orders;

    /**
     * 商品的收货地址的详细信息
     */
    private String addr;

    /**
     * 商品的收货地址的id
     */
    private Integer addressId;
}
