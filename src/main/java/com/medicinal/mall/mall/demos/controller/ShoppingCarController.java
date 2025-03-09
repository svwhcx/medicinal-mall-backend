package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.ShoppingCar;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.ShoppingCarService;
import com.medicinal.mall.mall.demos.vo.CartInfoVo;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.ShoppingCarInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 购物车方面的一些功能控制
 * @Author cxk
 * @Date 2025/2/26 18:18
 */

@RestController
@RequestMapping("/cart")
public class ShoppingCarController extends BaseController{

    @Autowired
    private ShoppingCarService shoppingCarService;

    @PostMapping
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> add(@RequestBody ShoppingCar shoppingCar){
        shoppingCarService.add(shoppingCar);
        return success();
    }

    /**
     * 查询当前用户的购物车的信息
     * @param pageQuery 分页参数
     * @return
     */
//    @GetMapping("")
//    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
//    public ResultVO<PageVo<ShoppingCarInfoVo>> listShoppingCar(PageQuery pageQuery){
//        return success(shoppingCarService.queryPage(pageQuery));
//    }


    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    @GetMapping
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<PageVo<CartInfoVo>> listShoppingCar(PageQuery pageQuery){
        return success(shoppingCarService.queryByPage(pageQuery));
    }

    /**
     * 根据ID将对应的商品移出购物车
     * @param ids 加入购物车的商品的id列表
     * @return
     */
    @DeleteMapping("/delete")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> deleteByIds(@RequestBody  List<Integer> ids){
        shoppingCarService.deleteById(ids);
        return success();
    }

    /**
     * 修改一个给谁车中的商品的预计的购买数量
     * @param shoppingCar 修改后的购物车信息
     * @return
     */
    @PutMapping("/update")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> update(@RequestBody ShoppingCar shoppingCar){
        shoppingCarService.updateById(shoppingCar);
        return success();
    }
}
