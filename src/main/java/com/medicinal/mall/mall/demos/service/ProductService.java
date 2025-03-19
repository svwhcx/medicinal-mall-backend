package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.query.ProductPageRequest;
import com.medicinal.mall.mall.demos.query.ProductRequest;
import com.medicinal.mall.mall.demos.vo.ProductVo;
import com.medicinal.mall.mall.demos.vo.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:20
 */
public interface ProductService {

    /**
     * 向系统添加一个商品信息
     * @param medicinalMaterialRequest 药材信息
     */
    void add(ProductRequest medicinalMaterialRequest);

    /**
     * 通过ID查询一个药材商品的详细信息
     * @param id
     * @return
     */
    ProductVo queryById(Integer id);

    /**
     * 商家发布一款商品
     * @param id 商品的ID
     */
    void publish(Integer id);

    /**
     * 根据商品的信息来修改一个商品（必须是当前商家的商品、以及当前商品没有发布才可以）
     * @param medicinalMaterialRequest 药材商品的信息
     */
    void update(ProductRequest medicinalMaterialRequest);

    /**
     * 分页查询商品的信息
     * @param productPageRequest 分页参数
     * @return 药材商品的分页信息
     */
    PageVo<Product> queryByPage(ProductPageRequest productPageRequest);

    /**
     * 商家分页获取商品信息
     * @param productPageRequest 分页数据
     * @return
     */
    PageVo<Product> sellerQueryByPage(ProductPageRequest productPageRequest);

    /**
     * 更改一个商品的上架状态
     * @param id 商品的id
     * @param status 商品的状态
     */
    void modifyStatus(Integer id, Integer status);

    /**
     * 删除一个商品
     * @param id 商品id
     */
    void delete(Integer id);



}
