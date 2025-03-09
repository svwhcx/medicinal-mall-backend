package com.medicinal.mall.mall.demos.pay;

import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.vo.OrderVo;

/**
 * @description 支付管理
 * @Author cxk
 * @Date 2025/3/5 21:35
 */
public interface IPay {

    /**
     * 用户支付一个订单
     * @param orderVo 要支付的订单信息
     */
    void pay(OrderVo orderVo);

    /**
     * 根据支付流水号判断支付操作是否成功！
     * @param payCode 支付流水号
     */
    void judgePay(String payCode);
}
