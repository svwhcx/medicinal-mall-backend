package com.medicinal.mall.mall.demos.constant;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 15:36
 */
public class RefundStatusConstant {

    /**
     * 正在申请退款
     */
    public static final Integer REFUND_STATUS_REFUNDING = 0;
    /**
     * 申请的退款被拒绝
     */
    public static final Integer REFUND_STATUS_ACCEPTED= 1;
    /**
     * 申请的订单退款被拒绝
     */
    public static final Integer REFUND_STATUS_REFUSED = 2;

    /**
     * 已经完成的订单
     */
    public static final Integer REFUND_STATUS_COMPLETED =3;

    /**
     * 取消了申请退款的操作。
     */
    public static final Object REFUND_STATUS_CANCELED = 4;

}
