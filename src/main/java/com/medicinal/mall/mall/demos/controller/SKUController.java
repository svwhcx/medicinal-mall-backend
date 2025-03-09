package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.entity.SKU;
import com.medicinal.mall.mall.demos.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/4 8:20
 */
@RestController
@RequestMapping("/sku")
public class SKUController extends BaseController{

    @Autowired
    private SkuService skuService;

    @PostMapping
    public ResultVO<Void> add(SKU sku){
        skuService.addSKU(sku);
        return success();
    }
}
