package com.medicinal.mall.mall.demos.service;

import com.medicinal.mall.mall.demos.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:19
 */
public interface PhotoService {

    /**
     * 向系统中上传一张图片
     * @param multipartFile 代表图片的文件。
     */
    Photo uploadPhoto(MultipartFile multipartFile) throws IOException;

    /**
     * 根据Id删除对应的图片
     * 记住这里是软删除，后续如果要清理的话可以使用脚本在服务器压力较低的时间段。
     * @param ids 表中的图片的ID列表
     */
    void deletePhotoByIds(List<Integer> ids);

    /**
     * 根据图片ID获取具体的图片地址
     * @param id 图片的ID
     * @return 包含图片地址的Photo对象
     */
    Photo getPhotoById(Integer id);

    /**
     * 根据图片的ID列表获取图片地址列表
     * @param ids 图片的ID列表
     * @return 包含图片地址的Photo集合。
     */
    List<Photo> getPhotoList(List<Integer> ids);
}
