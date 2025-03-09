package com.medicinal.mall.mall.demos.service.impl;

import cn.hutool.core.codec.Caesar;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medicinal.mall.mall.demos.dao.CategoryDao;
import com.medicinal.mall.mall.demos.entity.Category;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.CategoryService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/2 17:15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> list() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getIsDelete,false);
        return this.categoryDao.selectList(queryWrapper);
    }

    @Override
    public PageVo<Category> queryByPage(PageQuery pageQuery) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getIsDelete,false);
        queryWrapper.orderByAsc(false,Category::getSort);
        Page<Category> categoryPage = this.categoryDao.selectPage(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        return PageVo.build(categoryPage);
    }

    @Override
    public void add(Category category) {
        this.categoryDao.insert(category);
    }

    @Override
    public void modifyCategory(Category category) {
        this.categoryDao.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Category::getIsDelete,true);
        updateWrapper.eq(Category::getId,id);
        this.categoryDao.update(updateWrapper);
    }
}
