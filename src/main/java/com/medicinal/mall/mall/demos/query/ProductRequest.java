package com.medicinal.mall.mall.demos.query;

import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.entity.SKU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description 主要是给上传的前端一些额外的数据
 * @Author cxk
 * @Date 2025/2/27 17:10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest extends Product {

    /**
     * 商品对应的图片列表信息，也可以不使用该属性，直接放在SKU里面
     */
    private List<Integer> photoIds;

    /**
     * 商品对应的SKU信息
     */
    private List<SKU> skus;
}
