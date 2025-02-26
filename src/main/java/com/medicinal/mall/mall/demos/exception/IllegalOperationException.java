package com.medicinal.mall.mall.demos.exception;

import com.medicinal.mall.mall.demos.common.ResponseDataEnum;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 22:08
 */
public class IllegalOperationException extends BaseException {


    public IllegalOperationException(){
        super();
    }

    public IllegalOperationException(Integer code, String msg){
        super(code,msg);
    }

    public IllegalOperationException(ResponseDataEnum responseDataEnum){
        super(responseDataEnum);
    }

    public IllegalOperationException(String msg){
        super(msg);
    }
}
