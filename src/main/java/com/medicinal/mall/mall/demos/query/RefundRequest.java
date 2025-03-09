package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/6 15:40
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest {

    /**
     * 对应的退款的ID
     */
    private Integer refundId;

    private String refundReason;

    private String refuseReason;

    private String orderCode;

    private Integer orderId;

    private Integer goodsId;

    private List<String> imgs;

    private List<Integer> imgIds;

    /**
     * 商家是否同意退款
     */
    private Boolean isAgree;
}
