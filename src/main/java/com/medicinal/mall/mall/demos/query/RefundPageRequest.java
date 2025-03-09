package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 20:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundPageRequest extends PageQuery{


    /**
     * 订单退款的状态。
     */
    private Integer status;

    /**
     * 订单编号
     */
    private String orderCode;
}
