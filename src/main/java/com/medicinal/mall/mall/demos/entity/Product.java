package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("product")
public class Product {


    // 主键id
    @TableId
    private Integer id;

    // 卖家的id
    private Integer sellerId;

    // 当前商品的状态（主要有：0：上架；1：已发布；2：已下架）
    private Integer status = 0;

    // 当前药材商品的名称
    private String name;

    // 当前药材商品的描述信息
    @TableField("`description`")
    private String description;

    /**
     * 药材使用的注意事项
     */
    private String precautions;

    // 封面
    private String img;

    // 当前商品用于展示的详细信息的图片
    private String photos;

    // 当前商品的单价
    private BigDecimal price;

    // 当前商品的库存量
    private Integer stock;

    // 供应商的一些详细数据
    private String supplier;

    // 药材的出产地
    private String origin;

    // 药材的功效
    private String efficacy;

    // 商品的详细信息
    private String more_information;

    /**
     * 药材对应的品牌方
     */
    private String brand;

    /**
     * 商品上架的时间（没有发布就是上架的时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 保存时的SKU信息
     */
    @TableField(exist = false)
    private List<Integer> skuIds;

    /**
     * 商品种类
     */
    private Integer categoryId;

    /**
     * 商品的一些规格信息，不是SKU
     * 比如颜色，颜色的大小之类的。
     */
    private String specifications;
}
