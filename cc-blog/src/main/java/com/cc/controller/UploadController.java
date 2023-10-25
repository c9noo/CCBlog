package com.cc.controller;

import com.cc.result.ResponseResult;
import com.cc.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @program: CCBlog
 * @ClassName UploadController
 * @author: c9noo
 * @create: 2023-10-19 19:45
 * @Version 1.0
 **/
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 文件上传
     * @param img
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }

}
