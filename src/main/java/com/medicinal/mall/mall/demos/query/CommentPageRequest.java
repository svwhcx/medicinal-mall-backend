package com.medicinal.mall.mall.demos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/28 9:51
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageRequest extends PageQuery{

    /**
     * 商品的名称过滤
     */
    private String name;

    // 当前的商品的ID
    private Integer productId;

    /**
     * 评分等级， 用于商家查看自己的评论列表的。
     */
    private Integer level;

    /**
     * 评论的状态：<p>
     * 0：全部<p>
     * 1: 待回复<p>
     * 2: 已回复<p>
     */
    private Integer status;

    // 当前评论的类型（好评还是差评）: 0: 全部;1：好评;2:差评
    private Integer type = 0;

    // 评论是否已经回复（客户回复时就可以选择没有回复的评论进行回复了）
    private Boolean isReply;
}
