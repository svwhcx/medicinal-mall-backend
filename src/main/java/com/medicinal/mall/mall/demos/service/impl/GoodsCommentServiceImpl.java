package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.convert.CommentConvert;
import com.medicinal.mall.mall.demos.dao.GoodsCommentDao;
import com.medicinal.mall.mall.demos.dao.ProductDao;
import com.medicinal.mall.mall.demos.entity.GoodsComment;
import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.CommentPageRequest;
import com.medicinal.mall.mall.demos.service.GoodsCommentService;
import com.medicinal.mall.mall.demos.service.ProductService;
import com.medicinal.mall.mall.demos.service.PhotoService;
import com.medicinal.mall.mall.demos.service.UserService;
import com.medicinal.mall.mall.demos.vo.CommentVo;
import com.medicinal.mall.mall.demos.vo.ProductCommentVo;
import com.medicinal.mall.mall.demos.vo.ProductVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ProductService productService;


    @Autowired
    private PhotoService photoService;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserService userService;

    @Override
    public PageVo<CommentVo> queryByPage(CommentPageRequest commentPageRequest) {
        // 构建分页参数
        Page<GoodsComment> goodsCommentPage = new Page<>(commentPageRequest.getPageNum(), commentPageRequest.getPageSize());
        LambdaQueryWrapper<GoodsComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsComment::getGoodsId, commentPageRequest.getProductId());
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
            if (commentVo.getPhotos() == null) {
                continue;
            }
            // 分割图片，然后赋值给vo
            String[] split = commentVo.getPhotos().split(",");
            List<String> photoUrls = new ArrayList<>();
            for (String s : split) {
                Integer id = Integer.parseInt(s);
                photoUrls.add(this.photoService.getPhotoById(id).getAddr());
            }
            commentVo.setPhotoUrl(photoUrls);

            // 拼接用户的昵称
            commentVo.setUsername(this.userService.queryUserNameById(commentVo.getUserId()));
            commentVo.setNickname(this.userService.queryUserNameById(commentVo.getUserId()));
        }
        PageVo<CommentVo> pageVo = new PageVo<>();
        pageVo.setPageNum(goodsCommentPage.getCurrent());
        pageVo.setTotal(goodsCommentPage.getTotal());
        pageVo.setList(commentVoList);
        return pageVo;
    }

    @Override
    public PageVo<ProductCommentVo> sellerQueryByPage(CommentPageRequest commentPageRequest) {
        Integer seller = UserInfoThreadLocal.get().getUserId();
        // 根据条件查询当前用户的商品的id列表
        LambdaQueryWrapper<Product> productQueryWrapper = new LambdaQueryWrapper<>();
        if (commentPageRequest.getProductId() != null) {
            productQueryWrapper.eq(Product::getId, commentPageRequest.getProductId());
        }
        // 如果商品不为null，那么则按照名称筛选id出来
        if (commentPageRequest.getName() != null) {
            productQueryWrapper.like(Product::getName, "%" + commentPageRequest.getName() + "%");
        }
        // 根据条件筛选对应的商品id列表
        List<Product> products = this.productDao.selectList(productQueryWrapper);
        LambdaQueryWrapper<GoodsComment> queryWrapper = new LambdaQueryWrapper<>();
        if (commentPageRequest.getLevel() != null) {
            queryWrapper.eq(GoodsComment::getLevel, commentPageRequest.getLevel());
        }
        // 筛选当前评价的状态，可选择全部或者是否已经回复
        if (commentPageRequest.getStatus() != null) {
            if (commentPageRequest.getStatus() == 1) {
                queryWrapper.isNull(GoodsComment::getReply);
            } else if (commentPageRequest.getStatus() == 2) {
                queryWrapper.isNotNull(GoodsComment::getReply);
            }
        }
        List<Integer> productIds = products.stream().map(Product::getId).collect(Collectors.toList());
        queryWrapper.in(GoodsComment::getGoodsId, productIds);
        queryWrapper.orderByDesc(GoodsComment::getStartTime);
        // 根据条件分页查询评论列表
        Page<GoodsComment> goodsCommentPage = this.goodsCommentDao.selectPage(new Page<>(commentPageRequest.getPageNum(), commentPageRequest.getPageSize()), queryWrapper);

        // 组装数据
        Map<Integer, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, it -> it));
        List<ProductCommentVo> productCommentVos = new ArrayList<>();
        for (GoodsComment record : goodsCommentPage.getRecords()) {
            ProductCommentVo productCommentVo = new ProductCommentVo();
            // 如果已经回复则状态为2，否则为1
            productCommentVo.setId(record.getId());
            productCommentVo.setStatus(record.getReplyTime() != null ? 2: 1);
            productCommentVo.setName(productMap.get(record.getGoodsId()).getName());
            productCommentVo.setProductId(record.getGoodsId());
            productCommentVo.setImg(productMap.get(record.getGoodsId()).getImg());
            productCommentVo.setLevel(record.getLevel());
            productCommentVo.setTime(record.getReplyTime());
            productCommentVo.setStartTime(record.getStartTime());
            productCommentVo.setContent(record.getContent());
            productCommentVo.setImages(photoService.getPhotoUrlListByCombineIds(record.getPhotos()));
            productCommentVo.setUsername(this.userService.queryUserNameById(record.getUserId()));
            productCommentVos.add(productCommentVo);
        }
        PageVo<ProductCommentVo> pageVo = new PageVo<>();
        pageVo.setTotal(goodsCommentPage.getTotal());
        pageVo.setPageNum(goodsCommentPage.getCurrent());
        pageVo.setList(productCommentVos);
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
        commentVo.setStartTime(LocalDateTime.now());
        if (commentVo.getPhotoIds() != null && !commentVo.getPhotoIds().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Integer id : commentVo.getPhotoIds()) {
                sb.append(id).append(",");
            }
            commentVo.setPhotos(sb.toString());
        }

        // 保存评论
        goodsCommentDao.insert(commentVo);
    }

    @Override
    public void reply(CommentVo commentVo) {
        // 当然了，要看当前商品是否是当前商家的
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        // 从数据库中查询
        ProductVo medicinalMaterialVo = productService.queryById(commentVo.getGoodsId());
        // 查询当前商品是否存在
        if (medicinalMaterialVo == null) {
            throw new ParamException(ResponseDataEnum.PARAM_WRONG);
        }
        // 如果不是当前卖家的商品，则是非法操作
        if (!sellerId.equals(medicinalMaterialVo.getSellerId())) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        LambdaUpdateWrapper<GoodsComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(GoodsComment::getReply, commentVo.getReply());
        updateWrapper.eq(GoodsComment::getId, commentVo.getId());
        updateWrapper.set(GoodsComment::getReplyTime, LocalDateTime.now());
        goodsCommentDao.update(updateWrapper);
    }
}
