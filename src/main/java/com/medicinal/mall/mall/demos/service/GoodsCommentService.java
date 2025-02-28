package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.query.CommentPageRequest;
import com.medicinal.mall.mall.demos.vo.CommentVo;
import com.medicinal.mall.mall.demos.vo.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:20
 */
public interface GoodsCommentService {

    /**
     * 分页查询商品的评论
     * @param commentPageRequest 商品的评论
     */
    PageVo<CommentVo> queryByPage(CommentPageRequest commentPageRequest);

    /**
     * 用户在购买后，对商品进行评价
     * @param commentVo 添加一个评论
     */
    void add(CommentVo commentVo);

    /**
     * 商家可以在自己的商品管理页面查看当前的评论列表，并对一些评论进行回复操作
     * @param commentVo 回复的基本信息
     */
    void reply(CommentVo commentVo);
}
