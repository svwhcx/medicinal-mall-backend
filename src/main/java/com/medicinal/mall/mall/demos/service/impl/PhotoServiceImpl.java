package com.medicinal.mall.mall.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import java.util.List;
import java.util.UUID;

import static com.medicinal.mall.mall.demos.constant.PhotoMsgConstant.IMAGE_SAVE_PATH;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 18:22
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    private static final String URL_PREFIX = "http://localhost:";


    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private PhotoDao photoDao;


    @Override
    public Photo uploadPhoto(MultipartFile multipartFile) throws IOException {
        File dir =new File(IMAGE_SAVE_PATH);
        // 如果目录不存在则直接创建
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 检查图片后缀是否正常
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        FileUtil.validImageType(suffix);
        String uid = UUID.randomUUID().toString();
        String fileName = uid + suffix;
        File file = new File(IMAGE_SAVE_PATH, fileName);
        // 将文件存储到磁盘中
        multipartFile.transferTo(Paths.get(file.getPath()));
        // 获取url
        String photoAddr = URL_PREFIX+serverPort+"/"+fileName;
        Photo photo = new Photo();
        photo.setStartTime(LocalDateTime.now());
        photo.setAddr(photoAddr);
        this.photoDao.insert(photo);
        return photo;
    }

    @Override
    public void deletePhotoByIds(List<Integer> ids) {
        // 先获取图片
        List<Photo> photos = this.photoDao.selectByIds(ids);
        for (Photo photo : photos) {
            // 删除每个photo对应的图片
            File file = new File(photo.getAddr());
            if (file.exists()) {
                file.delete();
            }
            // 假删除
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
}
