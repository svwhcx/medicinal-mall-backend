package com.medicinal.mall.mall.demos.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 14:06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductVo {

    /**
     * 单个商品的订单Id
     */
    private Integer orderId;

    /**
     * 当前订单id的状态。
     */
    private Integer status;

    /**
     * 商品的id
     */
    private Integer productId;


    /**
     * 商品封面的图片
     */
    private String img;

    /**
     * 商品的价格
     */
    private BigDecimal price;

    /**
     * 商品的名称
     */
    private String name;

    /**
     * 本次订单商品的数量
     */
    private Integer num;

    /**
     * 当前商品是否已经发布了品论。
     */
    private Boolean isComment;


    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shippingTime;
}
