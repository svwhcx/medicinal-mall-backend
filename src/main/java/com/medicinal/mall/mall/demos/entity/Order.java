package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Date 2025/2/24 10:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("`order`")
public class Order {

    @TableId
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;


    /**
     * 当前订单的执行状态<p>
     *  0: 已创建<p>
     *  1:
     */
    private Integer status;

    /**
     * 当前订单对应的用户id
     */
    private Integer userId;

    /**
     * 当前订单对应的商品的卖家的id
     */
    private Integer sellerId;

    /**
     * 当前订单对应的商品id
     */
    private Integer goodsId;

    /**
     * 当前订单是否已经删除
     */
    private Boolean isDelete;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 用户的收获地址信息
     */
    private Integer addrId;

    /**
     * 本次订单的sku信息
     */
    private Integer skuId;

    /**
     * 本次购买的数量
     */
    private Integer buyNum;

    /**
     * 本次购买需要花费的金额。
     */
    private BigDecimal price;

    /**
     * 订单支付的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 订单完成的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    /**
     * 当前订单是否已经过期
     */
    private Boolean isExpire;


    /**
     * 订单的到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;


    /**
     * 订单的失效时间<p>
     * 这个时间就是订单默认的时间失效后的时间
     */
    private Long invalidationTime;


    /**
     * 订单发货的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime shippingTime;

    /**
     * 退款的id
     */
    private Integer refundId;
}
