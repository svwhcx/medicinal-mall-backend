package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.MedicinalMaterialDao;
import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import com.medicinal.mall.mall.demos.service.MedicinalMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:23
 */
@Service
public class MedicinalMaterialServiceImpl implements MedicinalMaterialService {

    @Autowired
    private MedicinalMaterialDao medicinalMaterialDao;

    @Override
    public void add(MedicinalMaterial medicinalMaterial) {
        // 插入数据
        // TODO 这里的id是发布这个商品的商家的id，而不是用户的id
        // 所以这里应该是sellerId;
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        medicinalMaterial.setSellerId(sellerId);
        medicinalMaterialDao.insert(medicinalMaterial);
    }

    @Override
    public MedicinalMaterial queryById(Integer id) {
        LambdaQueryWrapper<MedicinalMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicinalMaterial::getId, id);
        return medicinalMaterialDao.selectOne(queryWrapper);
    }
}
