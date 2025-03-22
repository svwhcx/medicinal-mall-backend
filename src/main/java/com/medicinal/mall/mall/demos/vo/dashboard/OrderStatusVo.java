package com.medicinal.mall.mall.demos.vo.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/7 15:36
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusVo {

    /**
     * 订单的状态对应的数量，
     * 如果是一周内的销售量，那么这个就是一条的销售量
     */
    private Long num;

    /**
     * 订单的状态。
     */
    private Integer groupId;

    private String name;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime keyTime;

    /**
     * 值 这里主要是用于最近一周的销售金额。
     * 这里定义的话就是一天的销售总额。
     */
    private Integer totalAmount;

}
