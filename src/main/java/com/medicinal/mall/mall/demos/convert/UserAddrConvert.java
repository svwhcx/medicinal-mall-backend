package com.medicinal.mall.mall.demos.convert;

import cn.hutool.core.util.ObjectUtil;
import com.medicinal.mall.mall.demos.entity.UserAddr;
import com.medicinal.mall.mall.demos.vo.UserAddrVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 22:43
 */
public class UserAddrConvert {

    /**
     * entity 2 vo
     * @param userAddr 地址信息
     * @return
     */
    public static UserAddrVo convert2Vo(UserAddr userAddr,Integer addrID) {
        UserAddrVo userAddrVo = new UserAddrVo();
        userAddrVo.setAddr(userAddr.getAddr());
        userAddrVo.setId(userAddr.getId());
        userAddrVo.setRecipient(userAddr.getRecipient());
        userAddrVo.setContactDetail(userAddr.getContactDetail());
        if (ObjectUtil.equal(addrID, userAddr.getId())){
            userAddrVo.setIsMainAddr(true);
        }
        return userAddrVo;
    }

    /**
     * entity列表 2 vo列表
     * @param userAddrList entity列表
     * @return
     */
    public static List<UserAddrVo> convert2VoList(List<UserAddr> userAddrList,Integer addrId){
        List<UserAddrVo> userAddrVoList = new ArrayList<>();
        for (UserAddr userAddr : userAddrList) {
            userAddrVoList.add(convert2Vo(userAddr,addrId));
        }
        return userAddrVoList;
    }
}
