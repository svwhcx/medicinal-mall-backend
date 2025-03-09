package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.Category;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.vo.PageVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/2 17:15
 */
public interface CategoryService {

    /**
     * 获取全部的商品分类信息
     * @return
     */
    List<Category> list();

    /**
     * 分页查询分类数据
     * @param pageQuery 分页详情
     * @return
     */
    PageVo<Category> queryByPage(PageQuery pageQuery);

    /**
     * 添加一个分类
     * @param category 分类信息
     */
    void add(Category category);

    /**
     * 修改一个分类信息
     * @param category 分类信息
     */
    void modifyCategory(Category category);

    /**
     * 根据id删除一个分类
     * @param id id
     */
    void deleteById(Integer id);

}
