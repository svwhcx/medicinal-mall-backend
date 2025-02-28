package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.Seller;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.query.UserRegistryRequest;
import com.medicinal.mall.mall.demos.service.SellerService;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:18
 */
@RestController
@RequestMapping("/seller")
public class SellerController extends BaseController {

    @Autowired
    private SellerService sellerService;


    /**
     * 用户的登录操作
     *
     * @param user 登录用户的一些基本信息，包括用户名和密码之类的。
     * @return
     */
    @PostMapping("/login")
    public ResultVO<UserLoginVo> login(@RequestBody User user) {
        return success(sellerService.userLogin(user));
    }


    /**
     * TODO 用户注册的时候的邮箱验证码操作
     * 后续的找回密码需要
     * 用户注册新用户的操作
     *
     * @param userRegistryRequest 用户注册的一些基本信息，包括用户名和密码之类的。
     * @return
     */
    @PostMapping("/register")
    public ResultVO<Void> register(@RequestBody UserRegistryRequest userRegistryRequest) {
        sellerService.register(userRegistryRequest);
        return success();
    }

    /**
     * 用户更新自己的个人信息
     * TODO 当修改自己绑定的邮箱的时候，需要验证新的邮箱是否能接受验证码之类的。
     *
     * @param seller 商家的个人信息
     * @return
     */
    @TokenVerify(isNeedInfo = true)
    @PutMapping("/update")
    public ResultVO<Void> update(@RequestBody Seller seller) {
        sellerService.updateById(seller);
        return success();
    }

    /**
     * TODO
     * 用户找回密码
     *
     * @param
     * @return
     */
    @PostMapping("/findPassword")
    public ResultVO<Void> findPassword(@RequestBody FindPasswordCmd findPasswordCmd) {
        sellerService.findPassword(findPasswordCmd);
        return success();
    }

    /**
     * 用户的退出登录操作
     *
     * @return
     */
    @PostMapping("/logout")
    public ResultVO<Void> logout(@RequestBody UserLoginVo userLoginVo) {
        sellerService.logout(userLoginVo);
        return success();
    }

    /**
     * 商家获取自己的个人信息
     * @return
     */
    @GetMapping("/info")
    @TokenVerify(RoleEnum.seller)
    public ResultVO<Seller> getSellerInfo(){
        return success(this.sellerService.getInfo());
    }
}
