package com.medicinal.mall.mall.demos.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicinal.mall.mall.demos.entity.SKU;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/4 0:12
 */
@Mapper
public interface SkuDao extends BaseMapper<SKU> {


    @Select("select sum(stock) from `sku`;")
    Long getAllStock();
}
