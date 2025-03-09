package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.UserAddr;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.UserAddrService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.UserAddrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户地址的管理
 * @description
 * @Author cxk
 * @Date 2025/2/24 11:46
 */
@RestController
@RequestMapping("/address")
public class UserAddrController extends BaseController {


    @Autowired
    private UserAddrService userAddrService;

    /**
     * 给用户添加一个地址
     * @param userAddr 待添加的用户地址
     * @return void
     */
    @PostMapping
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> addUserAddr(@RequestBody UserAddr userAddr){
        userAddrService.addUserAddr(userAddr);
        return success();
    }

    /**
     * 通过ID查询用户的收获地址的详细信息
     * @param userAddrId 用户的收获地址的Id
     * @return 对应的收获地址的详细信息
     */
    @GetMapping("/info")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<UserAddr> getUserAddrInfo(Integer userAddrId){
        return success(userAddrService.getAddrInfoById(userAddrId));
    }

    /**
     * 编辑修改某个地址的信息
     * @param userAddr
     */
    @PutMapping("/{id}")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> updateUserAddr(@PathVariable("id") Integer id,@RequestBody UserAddr userAddr){
        userAddr.setId(id);
        userAddrService.updateUserAddr(userAddr);
        return success();
    }


    /**
     * 分页查询用户的收获地址
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<PageVo<UserAddrVo>> listUserAddr(PageQuery pageQuery){
        return success(userAddrService.listAllUserAddr(pageQuery));
    }


    /**
     * 用户删除当前的自己的地址
     * @param userAddrId 删除的目标地址的id
     * @return
     */
    @DeleteMapping("")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> deleteUserAddr(Integer userAddrId){
        this.userAddrService.deleteUserAddr(userAddrId);
        return success();
    }

    /**
     * 用户设置一个地址为默认的收获地址
     * @param addrID 收获地址对应的ID
     * @return
     */
    @PutMapping("/{id}/default")
    @TokenVerify(value = RoleEnum.user,isNeedInfo = true)
    public ResultVO<Void> setAddrMain(@PathVariable("id") Integer addrID){
        this.userAddrService.setAddrMain(addrID);
        return success();
    }
}
