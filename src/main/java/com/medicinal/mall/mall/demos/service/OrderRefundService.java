package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.OrderRefund;
import com.medicinal.mall.mall.demos.query.RefundPageRequest;
import com.medicinal.mall.mall.demos.query.RefundRequest;
import com.medicinal.mall.mall.demos.vo.OrderRefundVo;
import com.medicinal.mall.mall.demos.vo.PageVo;

/**
 * @description 退款管理页面
 * @Author cxk
 * @Date 2025/3/6 0:48
 */
public interface OrderRefundService extends OrderService{

    /**
     * 退款一个订单
     * @param refundRequest 订单信息
     */
    void createRefund(RefundRequest refundRequest);

    /**
     * 商家接收一个退款
     * @param refundRequest 订单信息
     */
    void acceptRefund(RefundRequest refundRequest);

    /**
     * 用户取消一个退款。
     * @param refundRequest 退款的详细信息
     */
    void cancelRefund(Integer refundId);

    /**
     * 拒绝一个订单的退款操作
     * @param refundRequest 订单信息
     */
    void refuseRefund(RefundRequest refundRequest);

    /**
     * 商家分页获取自己的商品的退款列表
     * @param refundPageRequest 退款分页参数
     * @return
     */
    PageVo<OrderRefundVo> sellerQueryRefundList(RefundPageRequest refundPageRequest);

    /**
     * 商家或者用户查看自己的退款详情
     * @param refundId 退款id
     * @return
     */
    OrderRefund queryRefundDetail(Integer refundId);
}
