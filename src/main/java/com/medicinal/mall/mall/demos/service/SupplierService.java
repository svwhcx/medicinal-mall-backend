package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.Supplier;
import com.medicinal.mall.mall.demos.query.SupplierPageQuery;
import com.medicinal.mall.mall.demos.vo.PageVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/18 22:45
 */
public interface SupplierService {

    PageVo<Supplier> queryPage(SupplierPageQuery supplierPageQuery);

    List<Supplier> queryAll();

    Supplier getById(Integer id);

    void add(Supplier supplier);

    void update(Supplier supplier);

    void delete(Integer id);
}
