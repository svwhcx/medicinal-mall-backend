package com.medicinal.mall.mall;

import com.medicinal.mall.mall.demos.entity.GoodsComment;
import com.medicinal.mall.mall.demos.vo.CommentVo;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/28 11:33
 */
public class UnitTest {


    @Test
    public void test(){
        CommentVo commentVo = new CommentVo();
        commentVo.setId(1);
        commentVo.setContent("测试");
        commentVo.setUserId(1);
        commentVo.setStartTim(null);
        commentVo.setGoodsId(1);
        commentVo.setLevel(1);
        commentVo.setReply("测试");
        commentVo.setIsDelete(false);
        System.out.println(commentVo.getContent());
        GoodsComment goodsComment = (GoodsComment) commentVo;
        System.out.println(goodsComment.getContent());
    }


    @Test
    public void test2(){
        GoodsComment goodsComment = new GoodsComment();
        goodsComment.setContent("测试");
        goodsComment.setUserId(1);
        goodsComment.setStartTim(null);
        goodsComment.setGoodsId(1);
        goodsComment.setLevel(1);
        System.out.println(goodsComment.getContent());
        CommentVo commentVo = (CommentVo) goodsComment;
        System.out.println(commentVo.getContent());
    }
}
