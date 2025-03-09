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
 * @Date 2025/3/6 0:42
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_refund")
public class OrderRefund {

    /**
     * id
     *
     */
    @TableId
    private Integer id;

    /**
     * 用户退款的原因
     */
    private String reason;

    /**
     * 商家拒绝退款的原因
     */
    private String refuseReason;

    /**
     * 发起退款的用户的id
     */
    private Integer userId;

    /**
     * 订单的id
     */
    private Integer orderId;

    /**
     * 当前退款的状态
     */
    private Integer status;


    /**
     * 订单的编号
     */
    private String orderCode;

    /**
     * 退款的金额
     */
    private BigDecimal orderAmount;

    /**
     * 创建的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 附带的图片信息
     */
    private String images;

    /**
     * 这个是商家的id（方便后期查询）
     */
    private Integer sellerId;
}
