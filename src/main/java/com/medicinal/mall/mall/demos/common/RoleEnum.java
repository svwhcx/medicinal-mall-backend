package com.medicinal.mall.mall.demos.common;


public enum RoleEnum {

    user(0,"user"),
    admin(1,"admin"),
    seller(2, "seller"),
    ;

    private Integer roleId;
    private String roleName;

    RoleEnum(){}

    RoleEnum(Integer roleId,String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Integer getRoleId(){
        return this.roleId;
    }

    public String getRoleName(){
        return this.roleName;
    }
}
