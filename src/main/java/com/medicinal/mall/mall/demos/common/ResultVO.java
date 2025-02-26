package com.medicinal.mall.mall.demos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T>{

    private Integer code;

    private T data;

    private String msg;

    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.code = 200;
        resultVO.data = null;
        resultVO.msg = "SUCCESS";
        return  resultVO;
    }

    public static<T> ResultVO<T> success(T data){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.code = 200;
        resultVO.data = data;
        resultVO.msg = "SUCCESS";
        return resultVO;
    }

    public static ResultVO fail(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.code = code;
        resultVO.data = null;
        resultVO.msg = msg;
        return  resultVO;
    }

    public static ResultVO fail(ResponseDataEnum responseDataEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.code = responseDataEnum.getCode();
        resultVO.data = null;
        resultVO.msg = responseDataEnum.getMsg();
        return  resultVO;
    }

}
