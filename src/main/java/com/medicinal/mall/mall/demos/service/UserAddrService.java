package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.UserAddr;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.UserAddrVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 11:45
 */
public interface UserAddrService {

    /**
     * 给当前的用户添加一个地址
     * @param userAddr
     */
    void addUserAddr(UserAddr userAddr);

    /**
     * 查询用户的全部的地址
     */
    PageVo<UserAddrVo> listAllUserAddr(PageQuery pageQuery);

    /**
     * 更新用户的地址信息
     * @param userAddr
     */
    void updateUserAddr(UserAddr userAddr);


    /**
     * 设置用户的地址为默认地址
     * @param addrID
     */
    void setAddrMain(Integer addrID);

    /**
     * 根据ID查询一个地址的详细信息
     * @param userAddrId 收获地址的ID
     * @return
     */
    UserAddr getAddrInfoById(Integer userAddrId);

    /**
     * 根据地址ID来删除一个地址。
     * @param userAddrId 地址id
     */
    void deleteUserAddr(Integer userAddrId);
}
