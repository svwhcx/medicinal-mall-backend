package com.medicinal.mall.mall.demos.aop.annotation;


import com.medicinal.mall.mall.demos.common.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenVerify {

    /**
     * 要校验的角色
     * @return
     */
    RoleEnum[] value() default {RoleEnum.user};

    /**
     * 是否需要将解析的个人信息保存下来
     * @return
     */
    boolean isNeedInfo() default false;


}
