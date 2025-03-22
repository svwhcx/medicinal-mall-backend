package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.medicinal.mall.mall.demos.common.UserInfoThreadLocal;
import com.medicinal.mall.mall.demos.dao.PhotoDao;
import com.medicinal.mall.mall.demos.entity.Photo;
import com.medicinal.mall.mall.demos.service.PhotoService;
import com.medicinal.mall.mall.demos.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.medicinal.mall.mall.demos.constant.PhotoMsgConstant.IMAGE_SAVE_PATH;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:22
 */
@Service

public class PhotoServiceImpl implements PhotoService {

    private static final String URL_PREFIX = "file://";

    // 文件访问的前缀
    @Value("${application.url.prefix}")
    private String urlPrefix;

    @Value("${application.file.path}")
    private String filePath;

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private PhotoDao photoDao;


    @Override
    public Photo uploadPhoto(MultipartFile multipartFile) throws IOException {
        File dir = new File(filePath);
        // 如果目录不存在则直接创建
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 检查图片后缀是否正常
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        FileUtil.validImageType(suffix);
        String uid = UUID.randomUUID().toString();
        String fileName = uid + suffix;
        File file = new File(filePath, fileName);
        // 将文件存储到磁盘中
        multipartFile.transferTo(Paths.get(file.getPath()));
        // 获取url
        String photoAddr = urlPrefix + fileName;
        Photo photo = new Photo();
        photo.setStartTime(LocalDateTime.now());
        photo.setAddr(photoAddr);
        photo.setUserId(UserInfoThreadLocal.get().getUserId());
        this.photoDao.insert(photo);
        return photo;
    }

    @Override
    public void deletePhotoByIds(List<Integer> ids) {
        // 先获取图片
        List<Photo> photos = this.photoDao.selectByIds(ids);
        for (Photo photo : photos) {
            // 删除每个photo对应的图片
            FileUtil.deleteFile(filePath,photo.getAddr());
            // 数据库执行假删除操作，硬删除操作在服务器资源充足的情况下再来进行。
            // 例如使用一些shell脚本，或者在web服务中开启一个定时任务。
            LambdaUpdateWrapper<Photo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Photo::getId, photo.getId());
            updateWrapper.set(Photo::getIsDelete, true);
            this.photoDao.update(photo, updateWrapper);
        }
    }

    @Override
    public Photo getPhotoById(Integer id) {
        return this.photoDao.selectById(id);
    }

    @Override
    public List<Photo> getPhotoList(List<Integer> ids) {
        return this.photoDao.selectByIds(ids);
    }

    @Override
    public List<String> getPhotoUrlListByIds(List<Integer> ids) {
        List<String> photoUrlList = new ArrayList<>();
        for (Photo photo : this.photoDao.selectByIds(ids)) {
            photoUrlList.add(photo.getAddr());
        }
        return this.photoDao.selectByIds(ids).stream().map(Photo::getAddr).collect(Collectors.toList());
    }

    @Override
    public List<String> getPhotoUrlListByCombineIds(String ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        // 处理ids的分割
        String[] split = ids.split(",");
        List<Integer> photoIds = new ArrayList<>();
        for (String s : split) {
            if (s.isEmpty()) {
                continue;
            }
            Integer id = Integer.parseInt(s);
            photoIds.add(id);
        }
        if (photoIds.isEmpty()){
            return Collections.emptyList();
        }
        LambdaQueryWrapper<Photo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Photo::getId, photoIds).select(Photo::getAddr);
        List<Photo> photos = this.photoDao.selectList(queryWrapper);
        return photos.stream().map(Photo::getAddr).collect(Collectors.toList());
    }
}
