package com.medicinal.mall.mall.demos.constant;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/5 20:36
 */
public class OrderStatusConstant {

    /**
     * 订单刚刚创建
     */
    public static final Integer CREATED = 0;

    /**
     * 订单已经支付
     */
    public static final Integer PAYED = 1;

    /**
     * 订单已经发货
     */
    public static final Integer DELIVERED = 2;

    /**
     * 订单已经收货
     */
    public static final Integer RECEIVED = 3;

    /**
     * 订单已经完成
     */
    public static final Integer COMPLETED = 4;

    /**
     * 订单已经取消
     */
    public static final Integer CANCELED = 5;

    /**
     * 订单正在退款中。
     */
    public static final Integer REFUNDING = 6;

    /**
     * 订单已经退款
     */
    public static final Integer REFUNDED = 7;

    /**
     * 订单已经过期
     */
    public static final Integer EXPIRED = 8;

}
