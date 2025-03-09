package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.ProductCollectionDao;
import com.medicinal.mall.mall.demos.entity.ProductCollection;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.ProductCollectionService;
import com.medicinal.mall.mall.demos.service.ProductService;
import com.medicinal.mall.mall.demos.vo.FavoriteVo;
import com.medicinal.mall.mall.demos.vo.ProductVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:24
 */
@Service
public class ProductCollectionServiceImpl implements ProductCollectionService {

    @Autowired
    private ProductCollectionDao productCollectionDao;

    @Autowired
    private ProductService productService;

    @Override
    public void addCollection(Integer productId) {
        // 先查看商品是否存在
        ProductVo medicinalMaterialVo = productService.queryById(productId);
        if (medicinalMaterialVo == null){
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        Integer userId = UserInfoThreadLocal.get().getUserId();
        ProductCollection productCollection = new ProductCollection();
        productCollection.setUserId(userId);
        productCollection.setProductId(productId);
        productCollection.setStartTime(LocalDateTime.now());
        productCollectionDao.insert(productCollection);
    }

    @Override
    public void deleteCollection(Integer collectionId) {
        // 先查询当前商品是否是当前用户收藏的
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaUpdateWrapper <ProductCollection> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProductCollection::getUserId,userId)
                .eq(ProductCollection::getId,collectionId)
                .set(ProductCollection::getIsDelete,true)
                .set(ProductCollection::getDeleteTime,LocalDateTime.now());
        this.productCollectionDao.update(updateWrapper);
    }

    @Override
    public PageVo<FavoriteVo> queryByPage(PageQuery pageQuery) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<ProductCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCollection::getUserId,userId)
                .eq(ProductCollection::getIsDelete,false);
        IPage<ProductCollection> page = this.productCollectionDao.selectPage(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        List<FavoriteVo> favoriteVos = new ArrayList<>();
        for (ProductCollection record : page.getRecords()) {
            ProductVo medicinalMaterialVo = productService.queryById(record.getProductId());
            FavoriteVo favoriteVo = new FavoriteVo();
            favoriteVo.setId(record.getId());
            favoriteVo.setImg(medicinalMaterialVo.getImg());
            favoriteVo.setName(medicinalMaterialVo.getName());
            favoriteVo.setPrice(medicinalMaterialVo.getPrice());
            favoriteVo.setProductId(record.getProductId());
            favoriteVos.add(favoriteVo);
        }
        PageVo<FavoriteVo> pageVo = new PageVo<>();
        pageVo.setTotal(page.getTotal());
        pageVo.setPageNum(page.getCurrent());
        pageVo.setList(favoriteVos);
        return pageVo;
    }
}
