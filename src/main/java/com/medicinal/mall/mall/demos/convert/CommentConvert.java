package com.medicinal.mall.mall.demos.convert;

import com.medicinal.mall.mall.demos.entity.GoodsComment;
import com.medicinal.mall.mall.demos.vo.CommentVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/28 10:50
 */
public class CommentConvert {


    /**
     * 单个转换
     * @param goodsComment
     * @return
     */
    public static CommentVo convertToCommentVo(GoodsComment goodsComment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(goodsComment, commentVo);
        return commentVo;
    }

    /**
     * 批量转换
     * @param goodsCommentList
     * @return
     */
    public static List<CommentVo> comment2VoList(List<GoodsComment> goodsCommentList){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (GoodsComment goodsComment : goodsCommentList) {
            commentVoList.add(convertToCommentVo(goodsComment));
        }
        return commentVoList;
    }
}
