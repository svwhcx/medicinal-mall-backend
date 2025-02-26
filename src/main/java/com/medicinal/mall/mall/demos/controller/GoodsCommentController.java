package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.entity.GoodsComment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:17
 */
@RestController
@RequestMapping("/comment")
public class GoodsCommentController extends BaseController{

    /**
     * 分页获取对应的药材商品的评论详细信息
     * @param goodsId 商品的id
     * @return 评论列表
     */
    public ResultVO<GoodsComment> getCommentList(Integer goodsId){
        return success(null);
    }
}
