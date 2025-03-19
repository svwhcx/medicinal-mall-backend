package com.medicinal.mall.mall.demos.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/18 22:51
 */
@Setter
@Getter
public class SupplierPageQuery extends PageQuery{

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;
}
