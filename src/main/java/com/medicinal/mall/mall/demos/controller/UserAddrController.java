package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;
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
@RequestMapping("/userAddr")
public class UserAddrController extends BaseController {


    @Autowired
    private UserAddrService userAddrService;

    /**
     * 给用户添加一个地址
     * @param userAddr 待添加的用户地址
     * @return void
     */
    @PostMapping("/add")
    public ResultVO<Void> addUserAddr(UserAddr userAddr){
        userAddrService.addUserAddr(userAddr);
        return success();
    }

    /**
     * 通过ID查询用户的收获地址的详细信息
     * @param userAddrId 用户的收获地址的Id
     * @return 对应的收获地址的详细信息
     */
    @GetMapping("/info")
    public ResultVO<UserAddr> getUserAddrInfo(Integer userAddrId){
        return success(userAddrService.getAddrInfoById(userAddrId));
    }

    /**
     * 编辑修改某个地址的信息
     * @param userAddr
     */
    @PutMapping("/update")
    public void updateUserAddr(@RequestBody UserAddr userAddr){
        userAddrService.updateUserAddr(userAddr);
    }


    /**
     * 分页查询用户的收获地址
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("/list")
    public ResultVO<PageVo<UserAddrVo>> listUserAddr(PageQuery pageQuery){
        return success(userAddrService.listAllUserAddr(pageQuery));
    }


    public void deleteUserAddr(){

    }
}
