package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.entity.Category;
import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.query.ProductPageRequest;
import com.medicinal.mall.mall.demos.service.CategoryService;
import com.medicinal.mall.mall.demos.service.ProductService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/2 19:52
 */
@RestController
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private ProductService productService;

    /**
     * 获取全部的分类参数
     *
     * @return
     */
    @GetMapping
    public ResultVO<List<Category>> getCategoryList() {
        return success(this.categoryService.list());
    }

    /**
     * 添加一个分类
     * @param category 分类信息
     * @return
     */
    @PostMapping
    public ResultVO<Void> addCategory(@RequestBody Category category){
        this.categoryService.add(category);
        return success();
    }

    /**
     * 分页获取数据
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("/page")
    public ResultVO<PageVo<Category>> getPageCategory(PageQuery pageQuery){
        return success(this.categoryService.queryByPage(pageQuery));
    }

    /**
     * 在查看商品种类的时候根据类型来进行分页查询
     * @param productPageRequest 分页参数
     * @return
     */
    @GetMapping("/all/products")
    public ResultVO<PageVo<Product>> listAllProducts(ProductPageRequest productPageRequest) {
        return success(this.productService.queryByPage(productPageRequest));
    }

    /**
     * 修改一个Category
     * @param id 对应的id
     * @param category 分类
     * @return
     */
    @PutMapping("/{id}")
    public ResultVO<Void> modifyCategory(@PathVariable("id") Integer id,@RequestBody Category category){
        category.setId(id);
        this.categoryService.modifyCategory(category);
        return success();
    }

    /**
     * 根据ID删除一个分类
     * @param id 分类的id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultVO<Void> deleteCategory(@PathVariable("id") Integer id){
        this.categoryService.deleteById(id);
        return success();
    }
}
