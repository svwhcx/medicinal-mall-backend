package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.ShoppingCarDao;
import com.medicinal.mall.mall.demos.entity.MedicinalMaterial;
import com.medicinal.mall.mall.demos.entity.ShoppingCar;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.MedicinalMaterialService;
import com.medicinal.mall.mall.demos.service.ShoppingCarService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.ShoppingCarInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:23
 */
@Service
public class ShoppingCarServiceImpl implements ShoppingCarService {

    @Autowired
    private ShoppingCarDao shoppingCarDao;


    @Autowired
    private MedicinalMaterialService medicinalMaterialService;

    @Override
    public PageVo<ShoppingCarInfoVo> queryPage(PageQuery pageQuery) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<ShoppingCar> shoppingCarLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCarLambdaQueryWrapper.eq(ShoppingCar::getUserId,userId);
        IPage<ShoppingCar> page = shoppingCarDao
                .selectPage(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()),shoppingCarLambdaQueryWrapper);

        List<ShoppingCarInfoVo> shoppingCarInfoVoList = new ArrayList<>();

        // 根据medicinalId来查询出medicinalName和price
        for (ShoppingCar record : page.getRecords()) {
            MedicinalMaterial medicinalMaterial = medicinalMaterialService.queryById(record.getMedicinalId());
            ShoppingCarInfoVo shoppingCar =  new ShoppingCarInfoVo();
            shoppingCar.setId(record.getId());
            shoppingCar.setMedicinalId(medicinalMaterial.getId());
            shoppingCar.setPreBuyNum(record.getPreBuyNum());
            shoppingCar.setMedicinalName(medicinalMaterial.getName());
            shoppingCarInfoVoList.add(shoppingCar);
        }
        PageVo<ShoppingCarInfoVo> pageVo = new PageVo<>();
        pageVo.setPageNum(page.getCurrent());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(shoppingCarInfoVoList);

        return pageVo;
    }

    @Override
    public void deleteById(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return;
        }
        shoppingCarDao.deleteByIds(ids);
    }

    @Override
    public void add(ShoppingCar shoppingCar) {
        // TODO 如果当前商品没有库存的话，是否能够依然加入到购物车中去。
        shoppingCar.setUserId(UserInfoThreadLocal.get().getUserId());
        // TODO 顶多再做一个药材商品的完整性校验，防止污染数据库。
        shoppingCarDao.insert(shoppingCar);
    }

    @Override
    public void updateById(ShoppingCar shoppingCar) {
        // 还是要先验证当前的购物车的信息是否是当前用户的
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<ShoppingCar> shoppingCarLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCarLambdaQueryWrapper.eq(ShoppingCar::getUserId,shoppingCar.getId());
        ShoppingCar userShoppingCar = shoppingCarDao.selectOne(shoppingCarLambdaQueryWrapper);
        if (userShoppingCar == null){
            throw new ParamException(ResponseDataEnum.PARAM_WRONG);
        }
        // 验证当前的商品是否是当前的用户的购物车中的商品。
        if (userShoppingCar.getUserId().equals(userId)){
            throw new ParamException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 根据id来进行更新.
        shoppingCarDao.updateById(shoppingCar);
    }
}
