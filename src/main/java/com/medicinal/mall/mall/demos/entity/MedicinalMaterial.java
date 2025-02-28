package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.DocFlavor;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicinalMaterial {


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
    private String desc;

    // 封面
    private String img;

    // 当前商品用于展示的详细信息的图片
    private String photos;

    // 当前商品的单价
    private String price;

    // 当前商品的库存量
    private Integer num;

    // 供应商的一些详细数据
    private String supplier;

    // 药材的出产地
    private String productionLocation;

    // 药材的功效
    private String effect;

    // 商品的详细信息
    private String moreInformation;
}
