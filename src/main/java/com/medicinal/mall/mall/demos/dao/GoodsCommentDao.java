package com.medicinal.mall.mall.demos.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicinal.mall.mall.demos.entity.GoodsComment;
import com.medicinal.mall.mall.demos.vo.ProductCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:06
 */
@Mapper
public interface GoodsCommentDao extends BaseMapper<GoodsComment> {


//    List<ProductCommentVo> queryByPage(Integer productId, Integer type = 0, Boolean isReply);


    @Select("select count(level) from goods_comment where goods_id = #{goodsId}")
    long queryAllComments(@Param("goodsId") Integer goodsId);
}
