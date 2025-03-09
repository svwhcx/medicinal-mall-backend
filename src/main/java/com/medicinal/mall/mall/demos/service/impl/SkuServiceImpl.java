package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.medicinal.mall.mall.demos.dao.SkuDao;
import com.medicinal.mall.mall.demos.entity.SKU;
import com.medicinal.mall.mall.demos.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/4 0:13
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;


    @Override
    public List<SKU> listSkuByProductId(Integer productId) {
        LambdaQueryWrapper<SKU> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SKU::getProductId, productId);
        List<SKU> skus = skuDao.selectList(queryWrapper);
        return skus;
    }

    @Override
    @Transactional
    public void addSKU(SKU sku) {
        skuDao.insert(sku);
    }

    @Override
    public void updateSKU(SKU sku) {
        // 根据id修改SKU
        skuDao.updateById(sku);
    }
}
