package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.query.OrderPageRequest;
import com.medicinal.mall.mall.demos.query.OrderRequest;
import com.medicinal.mall.mall.demos.query.RefundRequest;
import com.medicinal.mall.mall.demos.vo.*;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:19
 */
public interface OrderService {

    /**
     * 分页查询自己的订单列表
     * @param orderPageRequest 分页参数
     * @return
     */
    PageVo<OrderListVo> userQueryByPage(OrderPageRequest orderPageRequest);

    /**
     * 用户分页查询自己的订单数据
     * @param orderPageRequest 分页参数
     * @return
     */
    PageVo<SingleOrderVo> userQueryOrdersPage(OrderPageRequest orderPageRequest);


    /**
     * 用户根据订单编号获取一个订单的详细信息<p>
     * 查询包括同意订单编号批次的地址，订单的支付时间等<p>
     * 还有就是当前订单编号下的各个商品订单的详细情况。
     * @param orderVo 包含订单编号的信息
     * @return
     */
    OrderDetailVo userQueryOrderDetailInfo(OrderVo orderVo);

    /**
     * 商家查询当前已有的订单列表（包括处理订单的状态）
     * @param orderPageRequest 分页参数
     * @return
     */
    PageVo<Order> sellerQueryByPage(OrderPageRequest orderPageRequest);


    /**
     * 商家分页查询自己的商品的各个订单信息
     * @param orderPageRequest 分页参数
     * @return
     */
    PageVo<SingleOrderVo> sellerQueryOrders(OrderPageRequest orderPageRequest);

    /**
     * 用户添加一个批次的商品订单信息
     * @param orderRequest 一个批次的商品订单信息
     * @return 订单创建成功后，用户需要的数据（订单编号，各个详细的订单的id信息）
     */
    OrderVo add(OrderRequest orderRequest);

    /**
     * 成功支付了一个订单
     * @param orderVo 当前的支付订单信息
     */
    void paySuccess(OrderVo orderVo);

     /**
     * 用户确认收货一个订单
     * @param orderId 订单信息信息
     */
    void completeOrder(Integer orderId);

    /**
     * 过期一个订单
     * @param order 订单信息
     */
    void expireOrder(Order order);

    /**
     * 修改一个订单的信息、只能修改收获地址
     * @param orderVo 订单信息
     */
    void updateOrder(OrderVo orderVo);

    /**
     * 取消一个订单
     * @param orderId 订单id信息
     */
    void cancelOrder(Integer orderId);



    /**
     * 根据订单id来恢复对应的商品的库存的数据
     * @param orderId 订单id
     */
    void recoverStock(Integer orderId);

    /**
     * 商品罚款一个订单
     * @param orderId 订单的id信息
     */
    void deliverOrder(Integer orderId);
}
