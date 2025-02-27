package com.medicinal.mall.mall.demos.util;

import com.medicinal.mall.mall.demos.common.ResponseDataEnum;
import com.medicinal.mall.mall.demos.constant.PhotoMsgConstant;
import com.medicinal.mall.mall.demos.exception.BaseException;
import com.medicinal.mall.mall.demos.exception.ParamException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/22 21:54
 */
public class FileUtil {


    private static String[] imageTypes = new String[]{".jpg",".png",".jpeg",".webp"};

    public static void checkSize(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        double size = (double) bytes.length / 1024 / 1024;
        if (size > 4){
            throw new ParamException("图片大小不能超过5M");
        }
    }

    public static void validImageType(String suffix){
        for (String imageType : imageTypes) {
            if (imageType.equalsIgnoreCase(suffix)){
                return;
            }
        }
        throw new BaseException(ResponseDataEnum.INVALID_IMAGE_TYPE);
    }

    public static void deleteFile(String url){
        // 处理url
        int i = url.lastIndexOf("/");
        String substring = url.substring(i+1);
        String fileUrl = PhotoMsgConstant.IMAGE_SAVE_PATH+ substring;
        File file = new File(fileUrl);
        file.delete();
    }

    public static void deleteFile(String[] urls){
        for (String url : urls) {
            deleteFile(url);
        }
    }

}
