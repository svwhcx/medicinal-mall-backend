package com.medicinal.mall.mall.demos.order;

import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.vo.OrderVo;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @description 将订单存储到redis中
 * @Author cxk
 * @Date 2025/3/5 21:54
 */
@Component("redisOrderManagement")
public class RedisOrderManagement implements IOrderManagement{


    @Override
    public void addOrderWork(OrderVo orderVo) {

    }

    @Override
    public void enOrderQueue(Order order) {

    }

    @Override
    public void outOrderQueue(Order order) {

    }
}
