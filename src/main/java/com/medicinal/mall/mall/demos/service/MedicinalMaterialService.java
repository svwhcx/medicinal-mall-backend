package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import com.medicinal.mall.mall.demos.query.MedicinalMaterialPageRequest;
import com.medicinal.mall.mall.demos.query.MedicinalMaterialRequest;
import com.medicinal.mall.mall.demos.vo.MedicinalMaterialVo;
import com.medicinal.mall.mall.demos.vo.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:20
 */
public interface MedicinalMaterialService {

    /**
     * 向系统添加一个商品信息
     * @param medicinalMaterialRequest 药材信息
     */
    void add(MedicinalMaterialRequest medicinalMaterialRequest);

    /**
     * 通过ID查询一个药材商品的详细信息
     * @param id
     * @return
     */
    MedicinalMaterialVo queryById(Integer id);

    /**
     * 商家发布一款商品
     * @param id 商品的ID
     */
    void publish(Integer id);

    /**
     * 根据商品的信息来修改一个商品（必须是当前商家的商品、以及当前商品没有发布才可以）
     * @param medicinalMaterialRequest 药材商品的信息
     */
    void update(MedicinalMaterialRequest medicinalMaterialRequest);

    /**
     * 分页查询商品的信息
     * @param medicinalMaterialPageRequest 分页参数
     * @return 药材商品的分页信息
     */
    PageVo<MedicinalMaterial> queryByPage(MedicinalMaterialPageRequest medicinalMaterialPageRequest);
}
