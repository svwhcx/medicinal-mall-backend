package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.service.UserService;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:20
 */


@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    /**
     * 用户的登录操作
     * @param user 登录用户的一些基本信息，包括用户名和密码之类的。
     * @return
     */
    @PostMapping("/login")
    public ResultVO<UserLoginVo> login(@RequestBody User user){
        return success(userService.userLogin(user));
    }


    /**
     *
     *
     * TODO 用户注册的时候的邮箱验证码操作
     * 后续的找回密码需要
     * 用户注册新用户的操作
     * @param user 用户注册的一些基本信息，包括用户名和密码之类的。
     * @return
     */
    @PostMapping("/register")
    public ResultVO<Void> register(@RequestBody User user){
        userService.register(user);
        return success();
    }

    /**
     * 用户更新自己的个人信息
     * @param user
     * @return
     */
    @TokenVerify(isNeedInfo = true)
    @PutMapping("/update")
    public ResultVO<Void> update(@RequestBody User user){
        userService.updateById(user);
        return success();
    }

    /**
     * TODO
     * 用户找回密码
     * @param userLoginVo
     * @return
     */
    @PostMapping("/findPassword")
    public ResultVO<Void> findPassword(@RequestBody FindPasswordCmd findPasswordCmd){
        userService.findPassword(findPasswordCmd);
        return success();
    }

    /**
     * 用户的退出登录操作
     * @return
     */
    @PostMapping("/logout")
    public ResultVO<Void> logout(@RequestBody UserLoginVo userLoginVo){
        userService.logout(userLoginVo);
        return success();
    }
}
