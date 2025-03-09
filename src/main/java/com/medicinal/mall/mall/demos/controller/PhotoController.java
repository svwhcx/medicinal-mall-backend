package com.medicinal.mall.mall.demos.controller;

import com.medicinal.mall.mall.demos.aop.annotation.TokenVerify;
import com.medicinal.mall.mall.demos.common.ResultVO;
import com.medicinal.mall.mall.demos.common.RoleEnum;
import com.medicinal.mall.mall.demos.entity.Photo;
import com.medicinal.mall.mall.demos.service.PhotoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:18
 */
@RestController
@RequestMapping("/photo")
public class PhotoController extends BaseController {

    @Autowired
    private PhotoService photoService;

    /**
     * 向系统中上传一张图片。
     * @param multipartFile 对应的图片文件。
     * @return 返回图片上传后的基本信息，包括图片的id已经图片的url地址。
     */
    @PostMapping("/upload")
    @TokenVerify(value = {RoleEnum.seller,RoleEnum.user},isNeedInfo = true)
    public ResultVO<Photo> uploadPhoto(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return success(photoService.uploadPhoto(multipartFile));
    }

    /**
     * 根据id删除一张图片文件。
     * @param ids 待删除的图片ID列表
     * @return
     */
    @DeleteMapping()
    @TokenVerify(value = {RoleEnum.seller,RoleEnum.user},isNeedInfo = true)
    public ResultVO<Void> deletePhoto(@RequestBody List<Integer> ids){
        photoService.deletePhotoByIds(ids);
        return success();
    }
}
