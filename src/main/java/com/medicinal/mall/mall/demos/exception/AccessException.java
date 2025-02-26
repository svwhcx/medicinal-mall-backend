package com.medicinal.mall.mall.demos.exception;


import com.medicinal.mall.mall.demos.common.ResponseDataEnum;

/**
 * @description
 * @Author cxk
 * @Date 2022/4/30 20:38
 */
public class AccessException extends BaseException{

    public AccessException(){
        super();
    }

    public AccessException(ResponseDataEnum responseDataEnum){
        super(responseDataEnum);
    }

    public AccessException(String msg){
        super(msg);
    }

}
