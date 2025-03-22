package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.dao.SupplierDao;
import com.medicinal.mall.mall.demos.entity.Supplier;
import com.medicinal.mall.mall.demos.query.SupplierPageQuery;
import com.medicinal.mall.mall.demos.service.SupplierService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/18 22:45
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public PageVo<Supplier> queryPage(SupplierPageQuery supplierPageQuery) {
        LambdaUpdateWrapper<Supplier> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Supplier::getIsDelete,0);
        queryWrapper.like(Strings.isNotBlank(supplierPageQuery.getName()),Supplier::getName,supplierPageQuery.getName());
        if (supplierPageQuery.getStatus()!=null){
            queryWrapper.eq(Supplier::getStatus,supplierPageQuery.getStatus());
        }
        Page<Supplier> supplierPage = supplierDao.selectPage(new Page<>(supplierPageQuery.getPageNum(), supplierPageQuery.getPageSize()), queryWrapper);
        return PageVo.build(supplierPage);
    }

    @Override
    public List<Supplier> queryAll() {
        LambdaQueryWrapper<Supplier> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Supplier::getIsDelete,0)
                .eq(Supplier::getStatus,1);
        return supplierDao.selectList(queryWrapper);
    }

    @Override
    public Supplier getById(Integer id) {
        return supplierDao.selectById(id);
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setIsDelete(false);
        this.supplierDao.insert(supplier);
    }

    @Override
    public void update(Supplier supplier) {
        supplierDao.updateById(supplier);
    }

    @Override
    public void delete(Integer id) {
        LambdaUpdateWrapper<Supplier> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Supplier::getIsDelete,1)
                .eq(Supplier::getId,id);
        supplierDao.update(lambdaUpdateWrapper);
    }
}
