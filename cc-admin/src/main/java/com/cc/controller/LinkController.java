package com.cc.controller;

import com.cc.pojo.dto.LinkDto;
import com.cc.pojo.entity.Link;
import com.cc.pojo.vo.LinkVo;
import com.cc.result.ResponseResult;
import com.cc.service.LinkService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: CCBlog
 * @ClassName LinkController
 * @author: c9noo
 * @create: 2023-10-24 21:00
 * @Version 1.0
 **/
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,Integer status){
        return ResponseResult.okResult(linkService.pageList(pageNum,pageSize,name,status));
    }

    @PostMapping
    public ResponseResult add(@RequestBody LinkDto linkDto){
        linkService.save(BeanCopyUtils.copyBean(linkDto, Link.class));
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id){
        return ResponseResult.okResult(BeanCopyUtils.copyBean(linkService.getById(id), LinkVo.class));
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkDto linkDto){
        linkService.updateById(BeanCopyUtils.copyBean(linkDto, Link.class));
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }

}
