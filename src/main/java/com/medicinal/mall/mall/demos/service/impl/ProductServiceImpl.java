package com.medicinal.mall.mall.demos.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.CategoryDao;
import com.medicinal.mall.mall.demos.dao.GoodsCommentDao;
import com.medicinal.mall.mall.demos.dao.ProductDao;
import com.medicinal.mall.mall.demos.entity.Category;
import com.medicinal.mall.mall.demos.entity.GoodsComment;
import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.entity.SKU;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.ProductPageRequest;
import com.medicinal.mall.mall.demos.service.ProductService;
import com.medicinal.mall.mall.demos.query.ProductRequest;
import com.medicinal.mall.mall.demos.service.PhotoService;
import com.medicinal.mall.mall.demos.service.SkuService;
import com.medicinal.mall.mall.demos.vo.ProductVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:23
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;


    @Autowired
    private PhotoService photoService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private CategoryDao categoryDao;


    @Autowired
    private GoodsCommentDao goodsCommentDao;

    @Override
    @Transactional
    public void add(ProductRequest productRequest) {
        // 插入数据
        // TODO 这里的id是发布这个商品的商家的id，而不是用户的id
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        Product product = new ProductRequest();
        BeanUtils.copyProperties(productRequest, product);
        product.setSellerId(sellerId);
        StringBuilder photos = new StringBuilder();
        for (Integer photoId : productRequest.getPhotoIds()) {
            photos.append(photoId).append(",");
        }
        product.setPhotos(photos.toString());
        product.setCreateTime(LocalDateTime.now());
        productDao.insert(product);
        // 插入后，需要商品的id信息，保存SKU数据
        for (SKU sku : productRequest.getSkus()) {
            sku.setId(null);
            sku.setProductId(product.getId());
            skuService.addSKU(sku);
        }
    }

    @Override
    public ProductVo queryById(Integer id) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, id);
        Product product = productDao.selectOne(queryWrapper);
        if (product == null) {
            throw new ParamException(ResponseDataEnum.MATERIAL_NOT_EXIST);
        }
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        // 将图片拆分出来
        Category category = this.categoryDao.selectById(product.getCategoryId());
        productVo.setCategory(category.getName());
        productVo.setPhotoUrl(photoService.getPhotoUrlListByCombineIds(product.getPhotos()));
        // 查询SKU信息
        productVo.setSkus(skuService.listSkuByProductId(product.getId()));
        // 计算库存
        for (SKU sku : productVo.getSkus()) {
            productVo.setStock(productVo.getStock() + sku.getStock());
        }
        // 获取好评总数
        /// 获取所有的评论等级
        long allLevels = this.goodsCommentDao.queryAllComments(product.getId());
        // 有多少条评论
        Long commentNum = this.goodsCommentDao.selectCount(new LambdaQueryWrapper<GoodsComment>().eq(GoodsComment::getGoodsId, product.getId()));
        // 计算平均的评论等级
        productVo.setRating(0);
        if (commentNum != 0){
            productVo.setRating((int) (allLevels / commentNum));
        }
        return productVo;
    }

    @Override
    public void publish(Integer id) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 校验当前的商品Id是否是当前的卖家上传的商品信息
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, id)
                .eq(Product::getSellerId, userId);
        Product product = this.productDao.selectOne(queryWrapper);
        // 如果没有查询到对应的商品信息，那么直接抛出异常
        if (product == null) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        Product material = new Product();
        product.setSellerId(userId);
        product.setId(id);
        material.setStatus(1);
        this.productDao.updateById(material);
    }

    @Override
    @Transactional
    public void update(ProductRequest medicinalMaterialRequest) {
        // 先检查当前的这个商品是否是当前的卖家的。
        Integer sellerId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, medicinalMaterialRequest.getId())
                .eq(Product::getSellerId, sellerId);
        Long count = productDao.selectCount(queryWrapper);
        // 如果当前的商品不是该用户的，则提示非法操作
        if (count <= 0) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 然后根据要修改的内容来进行修改
        Product product = (Product) medicinalMaterialRequest;
        // 合并图片的ID
        StringBuilder sb = new StringBuilder();
        for (Integer photoId : medicinalMaterialRequest.getPhotoIds()) {
            sb.append(photoId).append(",");
        }
        product.setPhotos(sb.toString());
        // 保存修改。
        productDao.updateById(product);
        for (SKU sku : medicinalMaterialRequest.getSkus()) {
            sku.setId(null);
            sku.setProductId(product.getId());
            skuService.addSKU(sku);
        }
    }

    @Override
    public PageVo<Product> queryByPage(ProductPageRequest productPageRequest) {
        // TODO 这里的时候，不管是商家还是用户，都可以进行类型的选择
        // 分页查询所有的
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getStatus,1);
        // 判断当前的是否是seller
        if (UserInfoThreadLocal.get() != null && UserInfoThreadLocal.get().getRoleId().equals(RoleEnum.seller.getRoleId())) {
            queryWrapper.eq(Product::getSellerId, UserInfoThreadLocal.get().getUserId());
            queryWrapper.eq(Product::getStatus, productPageRequest.getStatus());
        }
        if (productPageRequest.getCategoryId() != null) {
            queryWrapper.eq(Product::getCategoryId, productPageRequest.getCategoryId());
        }
//        Integer roleId = UserInfoThreadLocal.get().getRoleId();
//        Integer userId = UserInfoThreadLocal.get().getUserId();
//        if (roleId != null && roleId.equals(RoleEnum.seller.getRoleId())) {
//            queryWrapper.eq(Product::getSellerId, userId);
//            queryWrapper.eq(Product::getStatus, productPageRequest.getStatus());
//        }
        if (!ObjectUtil.isEmpty(productPageRequest.getName())) {
            // 对于名称应该是模糊匹配
            // TODO 或者使用全文检索
            queryWrapper.like(Product::getName, "%" + productPageRequest.getName() + "%");
        }
        // TODO 对于价格的过滤，可能后期会考虑使用Double类型的值来进行
        Page<Product> productPage = this.productDao.selectPage(new Page<>(productPageRequest.getPageNum(), productPageRequest.getPageSize()), queryWrapper);
        return PageVo.build(productPage);
    }

    @Override
    public PageVo<Product> sellerQueryByPage(ProductPageRequest productPageRequest) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getSellerId, UserInfoThreadLocal.get().getUserId());
        if (productPageRequest.getStatus() != null) {
            queryWrapper.eq(Product::getStatus, productPageRequest.getStatus());
        }
        if (productPageRequest.getCategoryId() != null) {
            queryWrapper.eq(Product::getCategoryId, productPageRequest.getCategoryId());
        }
        if (!ObjectUtil.isEmpty(productPageRequest.getName())) {
            // 对于名称是模糊匹配
            queryWrapper.like(Product::getName, "%" + productPageRequest.getName() + "%");
        }
        return PageVo.build(this.productDao.selectPage(new Page<>(productPageRequest.getPageNum(), productPageRequest.getPageSize()), queryWrapper));
    }

    @Override
    public void modifyStatus(Integer id, Integer status) {
        // 判断用户是不是
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getId, id)
                .eq(Product::getSellerId, userId);
        Product product = this.productDao.selectOne(queryWrapper);
        if (product == null) {
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Product::getId, id);
        updateWrapper.set(Product::getStatus, status);
        this.productDao.update(updateWrapper);
    }

    @Override
    public void delete(Integer id) {
        // 只有未上架得商品可以删除
        Product product = productDao.selectById(id);
        if (product == null){
            throw new ParamException(ResponseDataEnum.PARAM_WRONG);
        }
        if (product.getStatus() != 0){
            throw new ParamException(ResponseDataEnum.PRODUCT_DELETE_FORBBIT);
        }
        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Product::getIsDelete,true)
                        .eq(Product::getId,id);
        productDao.update(updateWrapper );
    }
}
