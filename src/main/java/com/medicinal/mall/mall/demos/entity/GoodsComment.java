package com.medicinal.mall.mall.demos.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description 商品的评论model
 * @Author cxk
 * @Date 2025/2/24 10:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("goods_comment")
public class GoodsComment {

    // 主键ID
    @TableId
    private Integer id;

    // 当前评论的内容
    private String content;

    // 当前评论的用户id
    private Integer userId;

    // 评论的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    // 商品的id
    private Integer goodsId;

    // 评论的好坏等级（好评还是差评）
    private Integer level;

    // 商家对该用户的评论的回复
    private String reply;

    // 当前评论是否被删除
    private Boolean isDelete;

    // 当前评论附带的图片信息
    private String photos;

    // 商家对此评论的回复时间。
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replyTime;
}
