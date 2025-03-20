package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.command.ChangePasswordCmd;
import com.medicinal.mall.mall.demos.command.FindPasswordCmd;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.query.UserPageQuery;
import com.medicinal.mall.mall.demos.query.UserRequest;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.UserLoginVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:16
 */
public interface UserService {

    /**
     * 用户登录操作
     * @param user 用户登录的信息
     * @return
     */
    UserLoginVo userLogin(UserRequest userRequest);

    /**
     * 用户注册操作，账号和密码之类的。
     * @param user
     */
    void register(UserRequest userRequest);

    /**
     * 用户更新自己的个人信息
     * @param user
     */
    void updateById(User user);

    /**
     * 用户退出系统登录操作
     * @param userLoginVo
     */
    void logout(UserLoginVo userLoginVo);

    /**
     * 用户找回密码操作（包括邮箱验证吗的验证）
     * @param userLoginVo
     */
    void findPassword(FindPasswordCmd findPasswordCmd);

    /**
     * 修改用户的默认收获地址
     * @param addrID 收获地址的ID
     */
    void setMainAddr(Integer addrID);

    /**
     * 根据用户的id获取用户的个人信息
     * @param userId 用户的id
     * @return 用户的基本信息
     */
    User getById(Integer userId);

    void changePassword(ChangePasswordCmd changePasswordCmd);

    /**
     * 根据用户的id查询用户名称
     * @param userId 用户的id
     * @return 用户的名称
     */
    String queryUserNameById(Integer userId);

    /**
     * 根据用户的id获取用户的默认收获地址
     * @param userID 用户的id
     * @return
     */
    Integer queryAddrByUserId(Integer userID);

    PageVo<User> list(UserPageQuery userPageQuery);

    /**
     * 管理员根据用户id修改用户的个人信息
     * @param user 用户信息
     */
    void updateUserById(User user);

    /**
     * 根据id修改用户的状态
     * @param id 用户的id
     * @param status 目标状态
     */
    void setUserStatus(Integer id, Integer status);
}
