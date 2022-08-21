package com.nathan.tuandui.bus.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /**
     * 根据 款号保存主图片图片路经
     */

    void saveMainPhoto (MultipartFile mainPhoto, String goodsNumber);

    /**
     * 保存细节图的路径
     * @param photos
     * @param goodsNumber
     */
    void savePhotos (MultipartFile[] photos, String goodsNumber);
    /**
     * 获取文件
     */
    Resource load(String rootPath, String secondaryPath, String filename);
}
