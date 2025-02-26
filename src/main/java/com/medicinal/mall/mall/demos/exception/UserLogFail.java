package com.medicinal.mall.mall.demos.exception;

import com.medicinal.mall.mall.demos.common.ResponseDataEnum;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 11:04
 */
public class UserLogFail extends BaseException{


    public UserLogFail(){
        super();
    }

    public UserLogFail(ResponseDataEnum responseDataEnum){
        super(responseDataEnum);
    }

    public UserLogFail(String msg){
        super(msg);
    }
}
