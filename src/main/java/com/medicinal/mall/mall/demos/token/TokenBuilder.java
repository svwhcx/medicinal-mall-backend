package com.medicinal.mall.mall.demos.token;


import com.medicinal.mall.mall.demos.util.TokenUtil;

import java.util.HashMap;

/**
 * @description 构建Token
 * @Author cxk
 * @Date 2022/4/30 17:39
 */
public class TokenBuilder {

    /**
     * 默认token有效时长 30 分钟
     */
    private static final int DEFAULT_VALIDATE_TIME = 1000*60*300;

    public static String buildToken(TokenInfo tokenInfo){
        return buildToken(tokenInfo,DEFAULT_VALIDATE_TIME);
    }

    public static String buildToken(TokenInfo tokenInfo,int millisecond){
        HashMap<String,Object> tokenData = new HashMap<>();
        tokenData.put("userId",tokenInfo.getUserId());
        tokenData.put("username",tokenInfo.getUsername());
        tokenData.put("roleId",tokenInfo.getRoleId());
        return TokenUtil.genToken(tokenData,millisecond);
    }

}
