package com.medicinal.mall.mall.demos.vo.dashboard;

import lombok.*;

/**
 * @description 对应仪表盘的顶部的数据
 * @Author cxk
 * @Date 2025/3/7 11:35
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmallData {

    /**
     * 小数据的标题
     */
    private String title;

    /**
     * 中间的数据
     */
    private String bigNum;

    /**
     * 底部的数据
     */
    private String smallNum;

}
