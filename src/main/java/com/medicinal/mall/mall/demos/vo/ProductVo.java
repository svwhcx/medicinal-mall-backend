package com.medicinal.mall.mall.demos.vo;

import com.medicinal.mall.mall.demos.entity.Product;
import com.medicinal.mall.mall.demos.entity.SKU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description 查看商品的详细信息的时候展示的内容
 * @Author cxk
 * @Date 2025/2/27 17:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo extends Product {

    // 关于本商品的一些图片的访问地址。
    private List<String> photoUrl;

    /**
     * SKU 信息
     */
    private List<SKU> skus;

    /**
     * 商品的分类，这里是字符串（需要后端做一下转换操作）
     */
    private String category;



    /**
     * 当前商品的好评率
     * 需要进行统计（好评率 = 总的评价等级 / 总评论数）
     */
    private Integer rating;
}
