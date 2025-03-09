package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description 用户在订单创建完成后，用户还需要进行后续的支付操作
 * @Author cxk
 * @Date 2025/3/6 10:23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {

    /**
     * 订单的Code，用户后续的支付状态。
     */
    private String orderCode;

    /**
     * 各个商品的订单id列表
     */
    private List<Integer> orderIds;

    /**
     * 订单的支付方式。TODO 1：支付宝，2：微信，3：其他
     * 暂时未支持其他的订单的支付方式。
     */
    private Integer payType;

    /**
     * 地址id
     */
    private Integer addrId;

    /**
     * 详细的地址信息
     */
    private String addr;

    /**
     * 订单的id<p>
     * 如果为null，那么就说明是多个商品一起的<p>
     * 如果不为null，那么则说明单个商品进行付款操作<p>
     */
    private Integer orderId;
}
