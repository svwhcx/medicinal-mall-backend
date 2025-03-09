package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/5 20:01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageRequest extends PageQuery{

    /**
     * 当前订单的状态
     */
    private Integer status;

    /**
     * 订单的编号
     */
    private String orderCode;

}
