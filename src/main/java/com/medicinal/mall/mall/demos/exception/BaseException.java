package com.medicinal.mall.mall.demos.exception;


import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import lombok.Getter;

/**
 * @description
 * @Author cxk
 * @Date 2022/4/30 19:26
 */
@Getter
public class BaseException extends RuntimeException{


    // 状态码
    private Integer code;
    protected ResponseDataEnum responseDataEnum;

    public BaseException(){
        super();
    }

    public BaseException(Integer code, String msg){
        super(msg);
        this.code = code;
    }
    public BaseException(String msg){
        super(msg);
    }

    public BaseException(ResponseDataEnum responseDataEnum){
        super(responseDataEnum.getMsg());
        this.responseDataEnum = responseDataEnum;
    }

}
