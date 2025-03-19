package com.medicinal.mall.mall.demos.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.medicinal.mall.mall.demos.constant.OrderStatusConstant;
import com.medicinal.mall.mall.demos.dao.OrderDao;
import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.service.OrderService;
import com.medicinal.mall.mall.demos.vo.OrderVo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @description 内存订单管理，将订单保存在内存中去
 * @Author cxk
 * @Date 2025/3/5 21:54
 */
@Primary
@Component("memoryOrderManagement")
public class MemoryOrderManagement implements IOrderManagement {


    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    /**
     * 延迟队列，用来处理订单超时没有付款的操作。
     */
    private final DelayQueue<OrderDelayWork> orderQueue = new DelayQueue<>();


    public MemoryOrderManagement() {
        new Thread(() -> {
            while (true) {
                try {
                    OrderDelayWork orderDelayWork = orderQueue.take();
                    // 到期后，查询订单的状态，如果订单状态还是未付款，那么则直接设置为已超时
                    LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Order::getOrderCode, orderDelayWork.getOrderVo().getOrderCode());
                    List<Order> orderList = this.orderDao.selectList(queryWrapper);
                    // 判断只要有一个没有完成支付，那么直接过期。
                    for (Order order : orderList) {
                        if (order.getStatus() < 1) {
                            expireOrder(order);
                        }
                    }
                    System.out.println("订单超时已被处理，订单号：" + orderDelayWork.getOrderVo().getOrderCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 配置一个订单的过期状态
     *
     * @param order 订单的状态
     */
    @Transactional
    public void expireOrder(Order order) {
        LambdaUpdateWrapper<Order> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.eq(Order::getId, order.getId());
        orderUpdateWrapper.set(Order::getIsExpire, true);
        orderUpdateWrapper.set(Order::getStatus, OrderStatusConstant.CANCELED);
        orderUpdateWrapper.set(Order::getUpdateTime, LocalDateTime.now());
        orderUpdateWrapper.set(Order::getExpireTime, LocalDateTime.now());
        orderDao.update(orderUpdateWrapper);
        orderService.recoverStock(order.getId());
    }

    @Override
    public void addOrderWork(OrderVo orderVo) {
        orderQueue.offer(new OrderDelayWork(System.currentTimeMillis() + DEFAULT_EXPIRE_TIME, orderVo));
    }

    @Override
    public void enOrderQueue(Order order) {
//        orderQueue.offer(new OrderDelayWork(System.currentTimeMillis()+DEFAULT_EXPIRE_TIME,order));
    }

    @Override
    public void outOrderQueue(Order order) {
        orderQueue.remove(order);
    }

    @Data
    class OrderDelayWork implements Delayed {

        /**
         * 订单的过期时间
         */
        private Long expireTime;

        /**
         * 订单的内容
         */
        private OrderVo orderVo;

        public OrderDelayWork(Long expireTime, OrderVo orderVo) {
            this.expireTime = expireTime;
            this.orderVo = orderVo;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diffTime = expireTime - System.currentTimeMillis();
            return unit.convert(diffTime, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            }
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            }
            return 0;
        }
    }
}
