package com.medicinal.mall.mall.demos.vo;

import com.medicinal.mall.mall.demos.entity.GoodsComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/28 10:06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo extends GoodsComment {

    // 当前评论涉及到的图片信息
    private List<String> photoUrl;

    // 提交评论时的图片id列表
    private List<Integer> photoIds;
}
