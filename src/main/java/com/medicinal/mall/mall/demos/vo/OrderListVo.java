package com.medicinal.mall.mall.demos.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 用于用户查询自己的订单列表的信息
 * @Author cxk
 * @Date 2025/3/6 17:31
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListVo {

    /**
     * 订单的编号
     */
    private String orderCode;

    /**
     * 创建的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 订单的状态。
     */
    private Integer status;

    /**
     * 订单内部的商品信息
     */
    private List<OrderProductVo> orderProductVoList;

    /**
     * 订单的总金额
     */
    private BigDecimal totalAmount;
}
