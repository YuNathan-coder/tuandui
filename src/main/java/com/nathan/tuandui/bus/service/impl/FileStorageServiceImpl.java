package com.nathan.tuandui.bus.service.impl;

import com.nathan.tuandui.bus.service.FileStorageService;
import com.nathan.tuandui.bus.service.GoodsService;
import com.nathan.tuandui.sys.common.consts.Constast;
import com.nathan.tuandui.sys.common.utils.AppFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 25517
 */
@Service

public class FileStorageServiceImpl implements FileStorageService, CommandLineRunner {

    @Autowired
    private GoodsService goodsService;

    @Override
    public void saveMainPhoto(MultipartFile mainPhoto, String goodsNumber) {

        String oldName = mainPhoto.getOriginalFilename();

        String newname = AppFileUtils.createNewFileName(oldName);



        File dirFile = new File(Constast.UPLOAD_FILE, goodsNumber);

        if (! dirFile.exists()) {

            dirFile.mkdirs();
        }


        File file = new File(dirFile, newname);

        try {
            mainPhoto.transferTo(file);
            String mainPhotoPath = file.toString();
            //更新对应的货物的主图片路径
            goodsService.updateMainPhoto(mainPhotoPath, goodsNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void savePhotos(MultipartFile[] photos, String goodsNumber) {

        String photosPath = "";
        for (int i = 0; i < photos.length; i ++) {
            String photoPath;
            MultipartFile photo = photos[i];

            String oldName = photo.getOriginalFilename();

            String newname = AppFileUtils.createNewFileName(oldName);



            File dirFile = new File(Constast.UPLOAD_FILE, goodsNumber);

            if (! dirFile.exists()) {

                dirFile.mkdirs();
            }


            File file = new File(dirFile, newname);

            try {
                photo.transferTo(file);
                if (i == photos.length -1) {
                    photoPath = file.toString();
                } else {
                    photoPath = file.toString() +",";
                }
                photosPath += photoPath;

                //更新对应的货物的主图片路径

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        goodsService.updatePhotos(photosPath, goodsNumber);

    }

    @Override
    public Resource load(String rootPath, String secondaryPath, String filename) {
        // 获取这个文件路径（默认放在 xx/操作人/filename ）
        Path file = Paths.get(rootPath).resolve(secondaryPath).resolve(filename);
        try {
            // 生成并返回这个文件对应的 Resource 对象
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
