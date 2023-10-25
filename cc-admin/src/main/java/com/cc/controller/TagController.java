package com.cc.controller;

import com.cc.pojo.dto.TagDto;
import com.cc.pojo.dto.TagListDto;
import com.cc.pojo.entity.Tag;
import com.cc.pojo.vo.PageVo;
import com.cc.pojo.vo.TagVo;
import com.cc.result.ResponseResult;
import com.cc.service.TagService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName TagController
 * @author: c9noo
 * @create: 2023-10-21 11:45
 * @Version 1.0
 **/
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        tagService.addTag(tagListDto);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTage(@PathVariable Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Long id){
     return ResponseResult.okResult(BeanCopyUtils.copyBean(tagService.getById(id), TagVo.class));
    }

    @PutMapping
    public ResponseResult putTage(@RequestBody TagDto tagDto){
        tagService.saveOrUpdate(BeanCopyUtils.copyBean(tagDto, Tag.class));
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }


}
