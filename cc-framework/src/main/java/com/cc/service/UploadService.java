package com.cc.service;

import com.cc.result.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: CCBlog
 * @ClassName UploadService
 * @author: c9noo
 * @create: 2023-10-20 10:13
 * @Version 1.0
 **/
public interface UploadService {
    /**
     * 文件上传
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
