package com.medicinal.mall.mall.demos.service.impl;

import com.medicinal.mall.mall.demos.dao.OrderRefundDao;
import com.medicinal.mall.mall.demos.entity.OrderRefund;
import com.medicinal.mall.mall.demos.service.OrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 0:48
 */
@Service
public class OrderRefundServiceImpl  {

    @Autowired
    private OrderRefundDao orderRefundDao;

}
