package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.entity.Seller;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.query.UserRegistryRequest;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;

/**
 * @description 商家的一些操作信息，里面的部分数据会和User进行共用
 * @Author cxk
 * @Date 2025/2/26 18:19
 */
public interface SellerService {
    /**
     * 商家的登录操作
     * @param user 商家的登录信息（这里直接和User进行共用了）
     * @return
     */
    UserLoginVo userLogin(User user);

    /**
     * 商家进行一个注册操作
     * @param userRegistryRequest 商家注册的信息
     */
    void register(UserRegistryRequest userRegistryRequest);

    /**
     * 商家修改一些个人信息
     * @param seller 商家的要修改的新的个人信息
     */
    void updateById(Seller seller);

    /**
     * 商家根据邮箱找回自己的密码
     * @param findPasswordCmd 找回密码的基本信息
     */
    void findPassword(FindPasswordCmd findPasswordCmd);

    /**
     * 一个商家退出登录的操作。
     * @param userLoginVo
     */
    void logout(UserLoginVo userLoginVo);

    /**
     * 商家获取自己的个人信息
     *
     * @return
     */
    Seller getInfo();

}
