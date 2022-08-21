package com.nathan.tuandui.bus.controller;

import com.nathan.tuandui.bus.service.FileStorageService;
import com.nathan.tuandui.sys.common.utils.OptResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadMainPhoto")
    public OptResult uploadMainPhoto (MultipartFile mainPhoto, String goodsNumber) {

        fileStorageService.saveMainPhoto(mainPhoto, goodsNumber);

        return OptResult.ok();
    }


    @PostMapping("/uploadPhotos")
    public OptResult uploadPhotos (MultipartFile[] photos, String goodsNumber) {
        fileStorageService.savePhotos(photos, goodsNumber);
        return OptResult.ok();
    }


    @GetMapping("/load")
    public Object load () {
        Resource file = fileStorageService.load("D:/upload/photo","Q123","F862ED115FA9410EB98ACA3B857EEF9E.jpg");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
