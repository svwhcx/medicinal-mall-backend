package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.command.ChangePasswordCmd;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.query.UserPageQuery;
import com.medicinal.mall.mall.demos.query.UserRequest;
import com.medicinal.mall.mall.demos.service.UserService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;
import com.svwh.parametercheck.annotation.MustEmail;
import com.svwh.parametercheck.annotation.NotNull;
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
    public ResultVO<UserLoginVo> login(@RequestBody UserRequest userRequest){
        return success(userService.userLogin(userRequest));
    }


    /**
     *
     *
     * TODO 用户注册的时候的邮箱验证码操作
     * 后续的找回密码需要
     * 用户注册新用户的操作
     * @param userRequest 用户注册的一些基本信息，包括用户名和密码之类的。
     * @return
     */
    @PostMapping("/register")
   public ResultVO<Void> register(@RequestBody UserRequest userRequest){
        userService.register(userRequest);
        return success();
    }

    /**
     * 用户更新自己的个人信息
     * TODO 当修改自己绑定的邮箱的时候，需要验证新的邮箱是否能接受验证码之类的。
     * @param user
     * @return
     */
    @PutMapping("/info")
    @TokenVerify(value = {RoleEnum.user},isNeedInfo = true)
    public ResultVO<Void> update(@RequestBody User user){
        userService.updateById(user);
        return success();
    }

    /**
     * 用户获取自己的个人信息
     * @return
     */
    @GetMapping("/info")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<User> getUserInfo(){
        Integer userId = UserInfoThreadLocal.get().getUserId();
        return success(userService.getById(userId));
    }

    /**
     * TODO
     * 用户找回密码
     * @param findPasswordCmd 找回密码的详细情况
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
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> logout(@RequestBody UserLoginVo userLoginVo){
        userService.logout(userLoginVo);
        return success();
    }


    /**
     * 用户修改自己的密码
     * @param changePasswordCmd
     * @return
     */
    @PutMapping("/password")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> changePassword(@RequestBody ChangePasswordCmd changePasswordCmd){
        userService.changePassword(changePasswordCmd);
        return success();
    }

    /**
     * 管理员获取用户的列表
     * @param userPageQuery 用户分页条件
     * @return
     */
    @GetMapping("/list")
    public ResultVO<PageVo<User>> list(UserPageQuery userPageQuery){
        PageVo<User> list = userService.list(userPageQuery);
        // 用户密码脱敏处理
        list.getList().forEach(user -> user.setPassword("***"));
        return success(list);
    }

    @PutMapping("/{id}/status")
    public ResultVO<Void> modifyStatus(@PathVariable("id") Integer id,@RequestBody User user){
        userService.setUserStatus(id,user.getStatus());
        return success();
    }

    /**
     * 管理员根据用户的id修改用户的个人信息
     * @param id 用户的id
     * @param user 用户的个人信息
     * @return
     */
    @PutMapping("/edit/{id}")
    public ResultVO<Void> edit(@PathVariable("id") Integer id,@RequestBody User user){
        user.setId(id);
        userService.updateUserById(user);
        return success();
    }

    /**
     * 管理员重置用户的密码
     * @param id 用户的id
     * @return
     */
    @PutMapping("/{id}/password/reset")
    public ResultVO<Void> resetPassword(@PathVariable("id") Integer id){
        User user = new User();
        user.setId(id);
        user.setPassword("123456");
        userService.updateUserById(user);
        return success();
    }
}
