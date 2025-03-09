package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 13:58
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {



    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单支付时间
     */
    private LocalDateTime payTime;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 收货地址。
     */
    private String addr;

    /**
     * 联系方式
     */
    private String contactDetailInfo;

    /**
     * 收货人姓名
     */
    private String receipt;


    /**
     * 本次订单的总金额
     */
    private BigDecimal totalAmount;

    /**
     * 同一个订单编号下的商品的信息。包括：封面，价格，数量，商品名称，是否已经品论
     */
    private List<OrderProductVo> orderProductVoList;
}
