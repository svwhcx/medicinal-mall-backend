package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.common.ResultVO;

import javax.xml.transform.Result;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:18
 */
public class BaseController {


    protected ResultVO<Void> fail(Integer code, String msg){
        return ResultVO.fail(400,"请求失败");
    }

    protected  <T> ResultVO<T> success(T data){
        return ResultVO.success(data);
    }


    protected ResultVO<Void> success(){
        return ResultVO.success();
    }
}
