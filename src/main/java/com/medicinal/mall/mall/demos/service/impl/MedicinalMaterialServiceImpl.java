package com.medicinal.mall.mall.demos.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.MedicinalMaterialDao;
import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.MedicinalMaterialPageRequest;
import com.medicinal.mall.mall.demos.service.MedicinalMaterialService;
import com.medicinal.mall.mall.demos.query.MedicinalMaterialRequest;
import com.medicinal.mall.mall.demos.service.PhotoService;
import com.medicinal.mall.mall.demos.vo.MedicinalMaterialVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:23
 */
@Service
public class MedicinalMaterialServiceImpl implements MedicinalMaterialService {

    @Autowired
    private MedicinalMaterialDao medicinalMaterialDao;


    @Autowired
    private PhotoService photoService;

    @Override
    public void add(MedicinalMaterialRequest medicinalMaterialRequest) {
        // 插入数据
        // TODO 这里的id是发布这个商品的商家的id，而不是用户的id
        // 所以这里应该是sellerId;
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        MedicinalMaterial medicinalMaterial = (MedicinalMaterial) medicinalMaterialRequest;
        medicinalMaterial.setSellerId(sellerId);
        StringBuilder photos = new StringBuilder();
        for (Integer photoId : medicinalMaterialRequest.getPhotoIds()) {
            photos.append(photoId).append(",");
        }
        medicinalMaterial.setPhotos(photos.toString());
        medicinalMaterialDao.insert(medicinalMaterial);
    }

    @Override
    public MedicinalMaterialVo queryById(Integer id) {
        LambdaQueryWrapper<MedicinalMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicinalMaterial::getId, id);
        MedicinalMaterial medicinalMaterial = medicinalMaterialDao.selectOne(queryWrapper);
        MedicinalMaterialVo medicinalMaterialVo = (MedicinalMaterialVo) medicinalMaterial;
        // 将图片拆分出来
        List<Integer> photoIds = new ArrayList<>();
        String[] split = medicinalMaterial.getPhotos().split(",");
        for (String photoId : split) {
            photoIds.add(Integer.parseInt(photoId));
        }
        medicinalMaterialVo.setPhotoUrl(photoService.getPhotoUrlListByIds(photoIds));
        return medicinalMaterialVo;
    }

    @Override
    public void publish(Integer id) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 校验当前的商品Id是否是当前的卖家上传的商品信息
        LambdaQueryWrapper<MedicinalMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicinalMaterial::getId, id)
                .eq(MedicinalMaterial::getSellerId, userId);
        MedicinalMaterial medicinalMaterial = this.medicinalMaterialDao.selectOne(queryWrapper);
        // 如果没有查询到对应的商品信息，那么直接抛出异常
        if (medicinalMaterial == null){
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        MedicinalMaterial material = new MedicinalMaterial();
        medicinalMaterial.setSellerId(userId);
        medicinalMaterial.setId(id);
        material.setStatus(1);
        this.medicinalMaterialDao.updateById(material);
    }

    @Override
    public void update(MedicinalMaterialRequest medicinalMaterialRequest) {
        // 先检查当前的这个商品是否是当前的卖家的。
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<MedicinalMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicinalMaterial::getId, medicinalMaterialRequest.getId())
                        .eq(MedicinalMaterial::getSellerId,sellerId);
        Long count = medicinalMaterialDao.selectCount(queryWrapper);
        // 如果当前的商品不是该用户的，则提示非法操作
        if (count <= 0){
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 然后根据要修改的内容来进行修改
        MedicinalMaterial medicinalMaterial = (MedicinalMaterial) medicinalMaterialRequest;
        // 合并图片的ID
        StringBuilder sb = new StringBuilder();
        for (Integer photoId : medicinalMaterialRequest.getPhotoIds()) {
            sb.append(photoId).append(",");
        }
        medicinalMaterial.setPhotos(sb.toString());
        // 保存修改。
        medicinalMaterialDao.updateById(medicinalMaterial);
    }

    @Override
    public PageVo<MedicinalMaterial> queryByPage(MedicinalMaterialPageRequest medicinalMaterialPageRequest) {
        // 分页查询所有的
        LambdaQueryWrapper<MedicinalMaterial> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isEmpty(medicinalMaterialPageRequest.getName())){
            // 对于名称应该是模糊匹配
            // TODO 或者使用全文检索
            queryWrapper.like(MedicinalMaterial::getName,"%"+medicinalMaterialPageRequest.getName()+"%");
        }
        // TODO 对于价格的过滤，可能后期会考虑使用Double类型的值来进行
        Page<MedicinalMaterial> medicinalMaterialPage = this.medicinalMaterialDao.selectPage(new Page<>(medicinalMaterialPageRequest.getPageNum(), medicinalMaterialPageRequest.getPageSize()), queryWrapper);
        return PageVo.build(medicinalMaterialPage);
    }
}
