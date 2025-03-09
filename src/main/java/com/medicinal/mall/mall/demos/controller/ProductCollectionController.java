package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.ProductCollection;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.ProductCollectionService;
import com.medicinal.mall.mall.demos.vo.FavoriteVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:18
 */
@RestController
@RequestMapping("/favorite")
public class ProductCollectionController extends BaseController {

    @Autowired
    private ProductCollectionService productCollectionService;

    /**
     * 用户将商品添加到收藏中
     * @param productCollection 商品收藏对象
     * @return
     */
    @PostMapping
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> addCollection(@RequestBody ProductCollection productCollection){
        productCollectionService.addCollection(productCollection.getProductId());
        return success();
    }


    /**
     * 用户移除一个收藏
     * @param collectionId 用户的收藏id
     * @return
     */
    @DeleteMapping("/{productId}")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> deleteCollection(@PathVariable("productId") Integer collectionId){
        productCollectionService.deleteCollection(collectionId);
        return success();
    }

    /**
     * 用户分页查询自己的收藏数据
     * @param pageQuery 具体的分页参数
     * @return
     */
    @GetMapping
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<PageVo<FavoriteVo>> listCollection(PageQuery pageQuery){
        return success(this.productCollectionService.queryByPage(pageQuery));
    }
}
