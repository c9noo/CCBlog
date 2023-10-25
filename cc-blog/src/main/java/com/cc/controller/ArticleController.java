package com.cc.controller;

import com.cc.result.ResponseResult;
import com.cc.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @program: CCBlog
 * @ClassName ArticleController
 * @author: c9noo
 * @create: 2023-10-16 11:01:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("/article")
@Api(tags = "文章管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 热门文章
     * @return
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    /**
     * 分页查询文章列表
     * @param pageSize
     * @param pageNum
     * @param categoryId
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageSize,Integer pageNum,Long categoryId){
        return articleService.articleList(pageSize,pageNum,categoryId);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable Long id){
        return articleService.getArticleDetail(id);
    }

    /**
     * 新增文章浏览量
     * @param id
     * @return
     */
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

}