package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.query.CommentPageRequest;
import com.medicinal.mall.mall.demos.service.GoodsCommentService;
import com.medicinal.mall.mall.demos.vo.CommentVo;
import com.medicinal.mall.mall.demos.vo.PageVo;

import com.medicinal.mall.mall.demos.vo.ProductCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 用户可以在付款后，对商品进行评价（可以附带一些图片），
 *              商家可以查看当前的商品部的所有用户的回复，并可以回复一条评论一个消息
 * @Author cxk
 * @Date 2025/2/26 18:17
 */
@RestController
@RequestMapping("/comment")
public class GoodsCommentController extends BaseController{

    @Autowired
    private GoodsCommentService goodsCommentService;

    /**
     * 分页获取对应的药材商品的评论详细信息
     * @param commentPageRequest 分页参数的详细信息
     * @return 评论列表
     */
    @GetMapping("/list")
    public ResultVO<PageVo<CommentVo>> getCommentList(CommentPageRequest commentPageRequest){
        return success( goodsCommentService.queryByPage(commentPageRequest));
    }

    /**
     * 用户在购买后，对一件商品进行评论
     * @param commentVo 评论的详细信息
     * @return
     */
    @PostMapping("/add")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> addComment(@RequestBody CommentVo commentVo){
        this.goodsCommentService.add(commentVo);
        return success();
    }

    /**
     * 管理员可以对一个评论进行回复功能
     * @param commentVo 评论的列表
     * @return
     */
    @PostMapping("/reply")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> replyComment(@RequestBody CommentVo commentVo){
        this.goodsCommentService.reply(commentVo);
        return success();
    }


    @GetMapping("/seller")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<PageVo<ProductCommentVo>> sellerGetCommentList(CommentPageRequest commentPageRequest){
        return success(goodsCommentService.sellerQueryByPage(commentPageRequest));
    }
}
