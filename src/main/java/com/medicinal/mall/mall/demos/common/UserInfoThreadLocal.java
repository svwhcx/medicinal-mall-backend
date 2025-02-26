package com.medicinal.mall.mall.demos.common;


import com.medicinal.mall.mall.demos.token.TokenInfo;

/**
 * @description
 * @Author cxk
 * @Date 2022/4/30 20:31
 */
public class UserInfoThreadLocal {

    private static final ThreadLocal<TokenInfo> USER_INFO = new ThreadLocal<>();

    public static void put(TokenInfo tokenInfo){
        USER_INFO.set(tokenInfo);
    }

    public static TokenInfo get(){
        return USER_INFO.get();
    }

    public static void remove(){
        USER_INFO.remove();
    }

}
