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
 * @description
 * @Author cxk
 * @Date 2025/2/24 10:08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("photo")
public class Photo {

    // 主键id
    @TableId
    private Integer id;

    // 图片的访问地址
    private String addr;

    // 图片上传的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    // 当前图片是否已经删除
    private Boolean isDelete;

    // 删除图片的时间。
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;

    /**
     * 用户id
     */
    private Integer userId;
}
