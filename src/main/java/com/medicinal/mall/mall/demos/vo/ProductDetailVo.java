package com.medicinal.mall.mall.demos.vo;

import com.medicinal.mall.mall.demos.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 获取一些详细信息
 * @Author cxk
 * @Date 2025/3/5 15:10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailVo extends Product {

    /**
     * 商品的分类，这里需要文字显示
     */
    private String category;


}
