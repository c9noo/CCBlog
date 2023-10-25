package com.cc.controller;

import com.cc.result.ResponseResult;
import com.cc.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @program: CCBlog
 * @ClassName UploadController
 * @author: c9noo
 * @create: 2023-10-24 15:26
 * @Version 1.0
 **/
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        return uploadService.uploadImg(multipartFile);
    }
}
