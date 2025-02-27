package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.ShoppingCar;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.ShoppingCarInfoVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:19
 */
public interface ShoppingCarService {


    /**
     * 分页查询购物车的数据
     * @param pageQuery 分页参数
     */
    PageVo<ShoppingCarInfoVo> queryPage(PageQuery pageQuery);

    /**
     * 根据ID列表将购物车中的商品移出用户的购物车
     * @param ids 购物车商品的Id合集
     */
    void deleteById(List<Integer> ids);

    /**
     * 将一个商品添加到购物车中去
     * @param shoppingCar 要添加的信息
     */
    void add(ShoppingCar shoppingCar);

    /**
     * 根据购物车中的商品id来更新新的预购的商品的数量。
     * @param shoppingCar 修改后的购物车的某个商品的数量。
     */
    void updateById(ShoppingCar shoppingCar);
}
