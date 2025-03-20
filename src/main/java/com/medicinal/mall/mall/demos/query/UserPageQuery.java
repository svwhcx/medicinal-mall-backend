package com.medicinal.mall.mall.demos.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/19 22:37
 */
@Getter
@Setter
public class UserPageQuery extends PageQuery{

    private String username;

    private Integer status;

    private String email;

}
