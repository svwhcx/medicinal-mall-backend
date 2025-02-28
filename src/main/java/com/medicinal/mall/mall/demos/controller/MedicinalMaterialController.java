package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import com.medicinal.mall.mall.demos.query.MedicinalMaterialPageRequest;
import com.medicinal.mall.mall.demos.service.MedicinalMaterialService;
import com.medicinal.mall.mall.demos.query.MedicinalMaterialRequest;
import com.medicinal.mall.mall.demos.vo.MedicinalMaterialVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 这里就是关于一些商品信息的接口了
 * @Author cxk
 * @Date 2025/2/26 18:18
 */
@RestController
@RequestMapping("/medicinal")
public class MedicinalMaterialController extends BaseController {

    @Autowired
    private MedicinalMaterialService medicinalMaterialService;

    /**
     * 商家添加一款商品信息
     * 需要注意的是，这里也要提供该商品是否要进行上架的操作。
     * 还是说需要系统自动上架之类的。
     * @param medicinalMaterialRequest 要上架的图片的信息
     * @return
     */
    @PostMapping("/add")
    public ResultVO<Void> addMedicinalMaterial(MedicinalMaterialRequest medicinalMaterialRequest){
        medicinalMaterialService.add(medicinalMaterialRequest);
        return success();
    }

    /**
     * 分页查询所有的药材商品信息
     * 注意，这里可能会有一些材料的分类之类的。
     * 还要提供模糊搜索
     * @param medicinalMaterialPageRequest 基本的分页参数。
     * @return 分页查询的结果
     */
    @GetMapping("/list")
    public ResultVO<PageVo<MedicinalMaterial>> getMedicinalMaterialList(MedicinalMaterialPageRequest medicinalMaterialPageRequest){
        return success(medicinalMaterialService.queryByPage(medicinalMaterialPageRequest));
    }

    /**
     * 根据药材商品的id来获取商品的详细信息
     * @param id 商品的id
     * @return
     */
    @GetMapping("/info")
    public ResultVO<MedicinalMaterialVo> getMedicinalMaterialInfo(Integer id){
        return success(medicinalMaterialService.queryById(id));
    }

    /**
     * 卖家根据商品的id来发布一款商品（因为之前可能没有发布）
     * @param id
     * @return
     */
    @PutMapping("/publish")
    public ResultVO<Void> publishMedicinalMaterial(Integer id){
        medicinalMaterialService.publish(id);
        return success();
    }

    /**
     * 更改一条商品的信息。
     * 关键是要未发布的时候才能修改。
     * @param medicinalMaterialRequest 要修改的商品信息
     * @return
     */
    @PutMapping("/update")
    public ResultVO<Void> updateMedicinalMaterial(MedicinalMaterialRequest medicinalMaterialRequest){
        medicinalMaterialService.update(medicinalMaterialRequest);
        return success();
    }

}
