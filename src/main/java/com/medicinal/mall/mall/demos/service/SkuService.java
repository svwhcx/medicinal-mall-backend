package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.SKU;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/4 0:13
 */
public interface SkuService {

    /**
     * 根据商品id来获取SKU信息
     * @param productId 商品id
     * @return
     */
    public List<SKU> listSkuByProductId(Integer productId);

    /**
     * 添加一个SKU信息
     * @param sku SKU信息
     */
    void addSKU(SKU sku);

    /**
     * 修改SKU的数据，包括添加库存等等
     * @param sku SKU信息
     */
    void updateSKU(SKU sku);
}
