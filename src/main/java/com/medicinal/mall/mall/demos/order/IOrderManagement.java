package com.medicinal.mall.mall.demos.order;

import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.vo.OrderVo;

/**
 * @description 订单管理
 * @Author cxk
 * @Date 2025/3/5 21:33
 */
public interface IOrderManagement {

    /**
     * 订单默认的超时时间<p>
     * 默认可超时：15分钟.
     */
    long DEFAULT_EXPIRE_TIME = 1000*60*15;

    /**
     * 添加一个过期的订单任务到队列中去。
     * @param orderVo 当前订单的信息
     */
    void addOrderWork(OrderVo orderVo);

    /**
     * 进入到订单队列
     * @param order
     */
    void enOrderQueue(Order order);

    /**
     * 将一个订单移除
     * @param order
     */
    void outOrderQueue(Order order);
}
