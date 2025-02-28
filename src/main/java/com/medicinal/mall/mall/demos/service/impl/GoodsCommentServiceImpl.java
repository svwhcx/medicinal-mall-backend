package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.convert.CommentConvert;
import com.medicinal.mall.mall.demos.convert.UserAddrConvert;
import com.medicinal.mall.mall.demos.dao.GoodsCommentDao;
import com.medicinal.mall.mall.demos.entity.GoodsComment;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.CommentPageRequest;
import com.medicinal.mall.mall.demos.service.GoodsCommentService;
import com.medicinal.mall.mall.demos.service.MedicinalMaterialService;
import com.medicinal.mall.mall.demos.service.PhotoService;
import com.medicinal.mall.mall.demos.vo.CommentVo;
import com.medicinal.mall.mall.demos.vo.MedicinalMaterialVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:22
 */
@Service
public class GoodsCommentServiceImpl implements GoodsCommentService {

    @Autowired
    private GoodsCommentDao goodsCommentDao;


    @Autowired
    private MedicinalMaterialService medicinalMaterialService;


    @Autowired
    private PhotoService photoService;

    @Override
    public PageVo<CommentVo> queryByPage(CommentPageRequest commentPageRequest) {
        // 构建分页参数
        Page<GoodsComment> goodsCommentPage = new Page<>(commentPageRequest.getPageNum(), commentPageRequest.getPageSize());
        LambdaQueryWrapper<GoodsComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsComment::getGoodsId, commentPageRequest.getMaterialId());
        queryWrapper.eq(GoodsComment::getIsDelete, 0);
        // 根据评论来进行筛选
        if (commentPageRequest.getType() == 1) {
            queryWrapper.ge(GoodsComment::getLevel, 4);
        } else if (commentPageRequest.getType() == 2) {
            queryWrapper.le(GoodsComment::getLevel, 3);
        }
        // 如果时普通用户，那就需要过滤掉评论的id
        this.goodsCommentDao.selectPage(goodsCommentPage, queryWrapper);

        // 拼接评论的图片地址
        List<CommentVo> commentVoList = CommentConvert.comment2VoList(goodsCommentPage.getRecords());
        for (CommentVo commentVo : commentVoList) {
            // 分割图片，然后赋值给vo
            String[] split = commentVo.getPhotos().split(",");
            List<String> photoUrls = new ArrayList<>();
            for (String s : split) {
                Integer id = Integer.parseInt(s);
                photoUrls.add(this.photoService.getPhotoById(id).getAddr());
            }
            commentVo.setPhotoUrl(photoUrls);
        }
        PageVo<CommentVo> pageVo = new PageVo<>();
        pageVo.setPageNum(goodsCommentPage.getCurrent());
        pageVo.setTotal(goodsCommentPage.getTotal());
        pageVo.setList(commentVoList);
        return pageVo;
    }

    @Override
    public void add(CommentVo commentVo) {

        Integer userId = UserInfoThreadLocal.get().getUserId();
        // TODO 检查用户当前是否已经购买过此商品
        // 也就是从订单表里面去查询

        // TODO 进行敏感词的过滤操作。
        // 转换一下评论
        commentVo.setUserId(userId);
        commentVo.setStartTim(LocalDateTime.now());
        StringBuilder sb = new StringBuilder();
        for (Integer id : commentVo.getPhotoIds()) {
            sb.append(id).append(",");
        }
        commentVo.setPhotos(sb.toString());
        // 保存评论
        goodsCommentDao.insert(commentVo);
    }

    @Override
    public void reply(CommentVo commentVo) {
        // 当然了，要看当前商品是否是当前商家的
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        // 从数据库中查询
        MedicinalMaterialVo medicinalMaterialVo = medicinalMaterialService.queryById(commentVo.getGoodsId());
        // 查询当前商品是否存在
        if (medicinalMaterialVo == null) {
            throw new ParamException(ResponseDataEnum.PARAM_WRONG);
        }
        // 如果不是当前卖家的商品，则是非法操作
        if (!sellerId.equals(medicinalMaterialVo.getSellerId())) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        commentVo.setReplyTime(LocalDateTime.now());
        goodsCommentDao.updateById(commentVo);
    }
}
