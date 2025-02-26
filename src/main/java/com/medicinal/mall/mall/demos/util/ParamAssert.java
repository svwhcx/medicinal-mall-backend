package com.medicinal.mall.mall.demos.util;


import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.exception.ParamException;

/**
 * @description
 * @Author cxk
 * @Date 2022/4/30 19:34
 */
public class ParamAssert {

    public static void notNull(Object obj){
        notNull(obj, ResponseDataEnum.PARAM_WRONG);
    }

    public static void notNull(Object object, ResponseDataEnum responseDataEnum){
        if (object == null){
            throw new ParamException(responseDataEnum);
        }
    }

    public static void notNull(Object obj,String msg){
        if (obj == null){
            throw new ParamException(msg);
        }
    }

    public static void isNull(Object obj){
        isNull(obj,ResponseDataEnum.PARAM_WRONG);
    }

    public static void isNull(Object obj,String msg){
        if (obj != null){
            throw new ParamException(msg);
        }
    }

    public static void isNull(Object obj,ResponseDataEnum responseDataEnum){
        if (obj != null){
            throw new ParamException(responseDataEnum);
        }
    }

    public static void notEquals(Object one,Object two,ResponseDataEnum responseDataEnum){
        ParamAssert.notNull(one);
        ParamAssert.notNull(two);
        if (!one.equals(two)){
            throw new ParamException(responseDataEnum);
        }
    }


    public static void isTrue(Boolean result,String msg){
        ParamAssert.notNull(result);
        if (!result){
            throw new ParamException(msg);
        }
    }

    public static void isTrue(Boolean result,ResponseDataEnum responseDataEnum){
        ParamAssert.notNull(result);
        if (!result){
            throw new ParamException(responseDataEnum);
        }
    }

}
