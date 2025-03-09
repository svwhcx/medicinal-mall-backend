package com.medicinal.mall.mall.demos.vo;

import com.medicinal.mall.mall.demos.entity.OrderRefund;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/7 21:42
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRefundVo extends OrderRefund {

    /**
     * 商品的名称
     */
    private String productName;


    /**
     * 商品的封面
     */
    private String productImage;

    /**
     * 商品的id
     */
    private Integer productId;

}
