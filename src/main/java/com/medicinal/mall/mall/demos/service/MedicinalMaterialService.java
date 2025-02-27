package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:20
 */
public interface MedicinalMaterialService {

    /**
     * 向系统添加一个商品信息
     * @param medicinalMaterial 药材信息
     */
    void add(MedicinalMaterial medicinalMaterial);

    /**
     * 通过ID查询一个药材商品的详细信息
     * @param id
     * @return
     */
    MedicinalMaterial queryById(Integer id);
}
