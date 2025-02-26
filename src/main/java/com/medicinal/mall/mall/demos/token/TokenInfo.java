package com.medicinal.mall.mall.demos.token;

import lombok.Data;
import lombok.ToString;

/**
 * @description
 * @Author cxk
 * @Date 2022/4/30 18:55
 */
@Data
@ToString
public class TokenInfo {

    private Integer userId;

    private Integer roleId;

    private String username;


    enum RoleEnum{

        user(0),
        admin(1);

        private Integer roleId;

        RoleEnum(){}

        RoleEnum(Integer roleId){
            this.roleId = roleId;
        }

        public Integer getRoleId(){
            return this.roleId;
        }

    }

    public static class Builder{

        TokenInfo tokenInfo = new TokenInfo();


        public Builder role(Integer roleId){
            tokenInfo.setRoleId(roleId);
            return this;
        }

        public Builder userId(Integer userId){
            tokenInfo.setUserId(userId);
            return this;
        }

        public Builder username(String username){
            tokenInfo.setUsername(username);
            return this;
        }

        public TokenInfo build(){
            return tokenInfo;
        }
    }

}
