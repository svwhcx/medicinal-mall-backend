package com.medicinal.mall.mall.demos.handler;

import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.exception.ParamException;
import com.medicinal.mall.mall.demos.exception.UserLogFail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description
 * @Author cxk
 * @Date 2022/4/30 19:37
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(ParamException.class)
    public ResultVO paramException(ParamException paramException){
        ResponseDataEnum responseDataEnum = paramException.getResponseDataEnum();
        if (responseDataEnum != null){
            return ResultVO.fail(responseDataEnum);
        }
        return ResultVO.fail(400,paramException.getMessage());
    }

    @ExceptionHandler(com.svwh.parametercheck.exception.ParamException.class)
    public ResultVO paramException(com.svwh.parametercheck.exception.ParamException paramException){
        return ResultVO.fail(ResponseDataEnum.PARAM_WRONG.getCode(), paramException.getMessage());
    }

    /**
     * 用户登录失败的异常类
     * @param userLogFail 用户登录失败的异常信息
     * @return
     */
    @ExceptionHandler(UserLogFail.class)
    public ResultVO userLogFailException(UserLogFail userLogFail){
        ResponseDataEnum responseDataEnum = userLogFail.getResponseDataEnum();
        if (responseDataEnum != null){
            return ResultVO.fail(responseDataEnum);
        }
        return ResultVO.fail(400,userLogFail.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO exception(Exception e){
        LOGGER.error("================系统发生了错误==========");
        LOGGER.error("发生错误的原因：{}",e.getMessage());
        LOGGER.error("异常链为   ",e.getCause());
        e.printStackTrace();
        return ResultVO.fail(ResponseDataEnum.SYSTEM_ERROR);
    }
}
