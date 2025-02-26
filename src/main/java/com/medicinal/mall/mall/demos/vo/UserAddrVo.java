package com.medicinal.mall.mall.demos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 当用户获取所有的地址信息时返回的Vo
 * @Author cxk
 * @Date 2025/2/26 22:13
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddrVo {

    // id
    private Integer id;

    // 详细的地址信息
    private String addr;

    // 是否时默认的地址（用于前端显示操作）
    private Boolean isMainAddr = false;

    // 收件人的姓名
    private String recipient;

    // 收件人的联系方式
    private String contactDetail;
}
