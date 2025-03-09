package com.medicinal.mall.mall.demos.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 商品评论vo对象
 * @Author cxk
 * @Date 2025/3/5 10:03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentVo {

    /**
     * 当前评论的id
     */
    private Integer id;

    /**
     * 商品的Id
     */
    private Integer productId;

    /**
     * 商品的名称
     */
    private String name;

    /**
     * 当前评论的状态，是否已经回复
     */
    private Integer status;

    /**
     * 商品的封面图
     */
    private String img;

    /**
     * 评论的等级
     */
    private Integer level;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 评论对应的用户昵称
     */
    private String username;

    /**
     * 评论的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    /**
     * 评论的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 当前评论的对应的图片
     */
    private List<String> images;
}
