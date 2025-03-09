package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.vo.FavoriteVo;
import com.medicinal.mall.mall.demos.vo.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:20
 */
public interface ProductCollectionService {

    /**
     * 用户将商品加入自己的收藏
     * @param productId 商品id
     */
    void addCollection(Integer productId);

    /**
     * 用户删除当前的收藏
     * @param collectionId 收藏的id
     */
    void deleteCollection(Integer collectionId);

    /**
     * 分页查询收藏
     * @param pageQuery
     */
    PageVo<FavoriteVo> queryByPage(PageQuery pageQuery);
}
