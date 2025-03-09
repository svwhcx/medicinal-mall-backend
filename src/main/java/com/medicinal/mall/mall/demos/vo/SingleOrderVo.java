package com.medicinal.mall.mall.demos.vo;

import com.medicinal.mall.mall.demos.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 这个是用于订单列表的单个项
 * @Author cxk
 * @Date 2025/3/7 17:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleOrderVo extends Order {

    /**
     * 如果当前订单还没有支付的话，那么则显示剩余的时间
     */
    private Long leftTime;

    /**
     * 商品的名称
     */
    private String name;


    /**
     * 商品的头像
     */
    private String img;

}
