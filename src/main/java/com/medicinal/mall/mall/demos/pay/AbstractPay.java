package com.medicinal.mall.mall.demos.pay;

import com.medicinal.mall.mall.demos.entity.Order;
import com.medicinal.mall.mall.demos.query.OrderRequest;
import com.medicinal.mall.mall.demos.service.OrderService;
import com.medicinal.mall.mall.demos.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/5 22:36
 */
public abstract class AbstractPay implements IPay{

    @Autowired
    protected OrderService orderService;


    protected void paySuccess(OrderVo orderVo){
        orderService.paySuccess(orderVo);
    }
}
