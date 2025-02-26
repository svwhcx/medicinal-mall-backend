package com.medicinal.mall.mall.demos.exception;


import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import org.apache.ibatis.annotations.Param;

/**
 * @description 参数错误异常
 * @Author cxk
 * @Date 2022/4/30 19:36
 */
public class ParamException extends BaseException{



    public ParamException(){
        super();
    }

    public ParamException(Integer code, String msg){
        super(code,msg);
    }

    public ParamException(ResponseDataEnum responseDataEnum){
        super(responseDataEnum);
    }

    public ParamException(String msg){
        super(msg);
    }

}
