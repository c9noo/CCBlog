package com.cc.controller;

import com.cc.result.ResponseResult;
import com.cc.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: CCBlog
 * @ClassName LinkController
 * @author: c9noo
 * @create: 2023-10-17 09:24
 * @Version 1.0
 **/
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 查询所有友链
     * @return
     */
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
