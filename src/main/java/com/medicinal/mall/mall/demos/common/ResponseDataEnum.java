package com.medicinal.mall.mall.demos.common;

import lombok.Getter;


@Getter
public enum ResponseDataEnum {

    SUCCESS(200,"SUCCESS"),
    SYSTEM_ERROR(500,"服务器发生了错误！"),
    PARAM_WRONG(1001,"错误的参数!"),
    INVALID_TOKEN(1002,"用户校验失败！"),
    NO_ACCESS(1003,"当前操作没有权限！"),
    VERIFICATION_ERROR(1004,"验证码错误！"),
    USERNAME_EXIST(1005,"用户名已被占用！"),
    PASSWORD_NOT_STRONG(1006,"密码强度不够！"),
    EMAIL_EXITS(1007,"该邮箱已注册！"),
    LOGIN_FAIL(1008,"账号或密码错误！"),
    EMAIL_NOT_EXIST(1009,"该邮箱暂未注册！"),
    TOKEN_NOT_EXIST(1010,"token 为空！"),
    DEL_RECRUIT_FAIL(1011,"删除招领失败！"),
    RECRUIT_NOT_EXIST(1012,"招领不存在！"),
    TOKEN_VERIFY_FAIL(1013,"token校验失败!"),
    FILE_URL_IS_NULL(1014,"非法的文件操作！文件URL不能为空"),
    ILLEGAL_OPERATION(1015,"禁止非法操作!"),
    INVALID_IMAGE_TYPE(1016, "非法的图片格式!" ),
    MATERIAL_NOT_EXIST(1017, "药材不存在!" ),
    NOT_MORE_MATERIAL(1018, "该商品已没有足够的库存!"),
    OLD_PASSWORD_ERROR(1019, "旧密码错误!"),
    NO_DEFAULT_ADDR(1020, "没有默认地址!"),
    ORDER_HAS_REFUND(1021,"当前订单还有未处理的退款，禁止操作！"),
    VERIFY_CODE_EXPIRED(1022,"验证码已过期！"),
    PRODUCT_DELETE_FORBBIT(1023,"已上架的商品禁止删除!")
    ;

    private Integer code;

    private String msg;

    ResponseDataEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }


}
