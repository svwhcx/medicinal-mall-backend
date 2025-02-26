package com.medicinal.mall.mall.demos.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.convert.UserAddrConvert;
import com.medicinal.mall.mall.demos.dao.UserAddrDao;
import com.medicinal.mall.mall.demos.entity.User;
import com.medicinal.mall.mall.demos.entity.UserAddr;
import com.medicinal.mall.mall.demos.exception.IllegalOperationException;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.query.PageQuery;
import com.medicinal.mall.mall.demos.service.UserAddrService;
import com.medicinal.mall.mall.demos.service.UserService;
import com.medicinal.mall.mall.demos.vo.PageVo;
import com.medicinal.mall.mall.demos.vo.UserAddrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 11:46
 */
@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Autowired
    private UserAddrDao userAddrDao;

    @Autowired
    private UserService userService;

    @Override
    public void addUserAddr(UserAddr userAddr) {
        // 校验一下用户的收获地址
        // 比如手机号是否是符合规范的
        userAddrDao.insert(userAddr);
    }

    @Override
    public PageVo<UserAddrVo> listAllUserAddr(PageQuery pageQuery) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<UserAddr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddr::getUserId, userId);
        Page<UserAddr> userAddrPage = userAddrDao.selectPage(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        // 获取用户的主id
        User user = userService.getById(userId);
        PageVo<UserAddrVo> pageVo = new PageVo<>();
        pageVo.setPageNum(userAddrPage.getCurrent());
        pageVo.setTotal(userAddrPage.getTotal());
        pageVo.setList(UserAddrConvert.convert2VoList(userAddrPage.getRecords(),user.getId()));
        return pageVo;
    }

    @Override
    public void updateUserAddr(UserAddr userAddr) {
        // 通过ThreadLocal获取当前的用户id
        LambdaUpdateWrapper<UserAddr> updateWrapper = new LambdaUpdateWrapper<>();
        if (!StringUtils.isEmpty(userAddr.getAddr())) {
            updateWrapper.set(UserAddr::getAddr, userAddr.getAddr());
        }
        if (!StringUtils.isEmpty(userAddr.getRecipient())) {
            updateWrapper.set(UserAddr::getRecipient, userAddr.getRecipient());
        }
        if (!StringUtils.isEmpty(userAddr.getContactDetail())) {
            updateWrapper.set(UserAddr::getContactDetail, userAddr.getContactDetail());
        }
        Integer userID = UserInfoThreadLocal.get().getUserId();
        updateWrapper.eq(UserAddr::getUserId, userID);
        // 进行更新
        userAddrDao.update(updateWrapper);
    }

    @Override
    public void setAddrMain(Integer addrID) {
        Integer userId = UserInfoThreadLocal.get().getUserId();
        // 先查询是否存在这个地址
        LambdaQueryWrapper<UserAddr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddr::getAddr, addrID);
        queryWrapper.eq(UserAddr::getUserId, userId);
        Long num = userAddrDao.selectCount(queryWrapper);
        // 如果当前不存在这个地址，直接抛出异常
        if (num == 0) {
            throw new ParamException("当前地址不存在！");
        }
        // 如果当前地址存在，则调用User服务进行更新
        userService.setMainAddr(addrID);
    }

    @Override
    public UserAddr getAddrInfoById(Integer userAddrId) {
        LambdaQueryWrapper<UserAddr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddr::getId, userAddrId);
        UserAddr userAddr = userAddrDao.selectOne(queryWrapper);
        Integer userId = UserInfoThreadLocal.get().getUserId();
        if (!ObjectUtil.equal(userId, userAddr.getUserId())) {
            // 如果当前的地址信息不是该用户的，则抛出非法操作异常
            throw new IllegalOperationException(ResponseDataEnum.ILLEGAL_OPERATION);
        }
        // 如果是合法则直接返回
        return userAddr;
    }
}
