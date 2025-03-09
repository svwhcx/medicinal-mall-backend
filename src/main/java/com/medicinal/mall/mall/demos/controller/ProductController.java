package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.query.ProductPageRequest;
import com.medicinal.mall.mall.demos.query.ProductRequest;
import com.medicinal.mall.mall.demos.service.ProductService;
import com.medicinal.mall.mall.demos.vo.ProductVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 这里就是关于一些商品信息的接口了
 * @Author cxk
 * @Date 2025/2/26 18:18
 */
@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 商家添加一款商品信息
     * 需要注意的是，这里也要提供该商品是否要进行上架的操作。
     * 还是说需要系统自动上架之类的。
     * @param productRequest 要上架的图片的信息
     * @return
     */
    @PostMapping
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> addMedicinalMaterial(@RequestBody ProductRequest productRequest){
        productService.add(productRequest);
        return success();
    }

    /**
     * 分页查询所有的药材商品信息
     * 注意，这里可能会有一些材料的分类之类的。
     * 还要提供模糊搜索
     * @param productPageRequest 基本的分页参数。
     * @return 分页查询的结果
     */
    @GetMapping
    public ResultVO<PageVo<Product>> getMedicinalMaterialList(ProductPageRequest productPageRequest){
        return success(productService.queryByPage(productPageRequest));
    }

    /**
     * 卖家分页获取自己的商品信息
     * @param productPageRequest 分页参数
     * @return
     */
    @GetMapping("/seller")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<PageVo<Product>> sellerGetProductLIst(ProductPageRequest productPageRequest){
        return success(productService.sellerQueryByPage(productPageRequest));
    }

    /**
     * 商家获取自己的商品列表
     * @param productPageRequest
     * @return
     */
    @GetMapping("/sellerList")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<PageVo<Product>> sellerGetMedicinalMaterialList(ProductPageRequest productPageRequest){
        return success(productService.queryByPage(productPageRequest));
    }

    /**
     * 根据药材商品的id来获取商品的详细信息
     * @param id 商品的id
     * @return
     */
    @GetMapping("{id}")
    public ResultVO<ProductVo> getMedicinalMaterialInfo(@PathVariable("id") Integer id){
        return success(productService.queryById(id));
    }

    /**
     * 商家更新一个商品的上架、下架的状态
     * @param id 商品的id
     * @param status 商品的状态
     * @return
     */
    @PutMapping("/{id}/{status}")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> modifyStatus(@PathVariable("id") Integer id,@PathVariable("status") Integer status){
        productService.modifyStatus(id,status);
        return success();
    }

    /**
     * 卖家根据商品的id来发布一款商品（因为之前可能没有发布）
     * @param id
     * @return
     */
    @PutMapping("/publish")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> publishMedicinalMaterial(Integer id){
        productService.publish(id);
        return success();
    }

//    @PutMapping("/removal/{id}")
//    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
//    public ResultVO<Void> removalProduct(@PathVariable("id")Integer id){
//
//
//        return success();
//    }

    /**
     * 更改一条商品的信息。
     * 关键是要未发布的时候才能修改。
     * @param productRequest 要修改的商品信息
     * @return
     */
    @PutMapping("/update")
    @TokenVerify(value = RoleEnum.seller,isNeedInfo = true)
    public ResultVO<Void> updateMedicinalMaterial(@RequestBody ProductRequest productRequest){
        productService.update(productRequest);
        return success();
    }

}
