package com.cc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.pojo.dto.AddArticleDto;
import com.cc.pojo.dto.ArticleUpdateDto;
import com.cc.pojo.entity.Article;
import com.cc.pojo.entity.ArticleTag;
import com.cc.pojo.entity.Tag;
import com.cc.pojo.vo.ArticleUpdateVo;
import com.cc.result.ResponseResult;
import com.cc.service.ArticleService;
import com.cc.service.ArticleTagService;
import com.cc.service.TagService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: CCBlog
 * @ClassName ArticleController
 * @author: c9noo
 * @create: 2023-10-24 15:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String title,String summary){

        return ResponseResult.okResult(articleService.selectList(pageNum,pageSize,title,summary));
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable Long id){
        ArticleUpdateVo articleUpdateVo =
                BeanCopyUtils.copyBean(articleService.getById(id), ArticleUpdateVo.class);
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> list = articleTagService.list(wrapper);
        articleUpdateVo.setTags( list.stream().map(
                ArticleTag::getTagId
        ).collect(Collectors.toList()));
        return ResponseResult.okResult(articleUpdateVo);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleUpdateDto articleUpdateDto){
        articleService.updateById(BeanCopyUtils.copyBean(articleUpdateDto, Article.class));

        // 删除特定文章的旧标签关联
        articleTagService.remove(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, articleUpdateDto.getId()));

        List<ArticleTag> collect = articleUpdateDto.getTags().stream()
                .map(tagId-> new ArticleTag(articleUpdateDto.getId(), tagId))
                .collect(Collectors.toList());

        articleTagService.saveBatch(collect);

        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }

}
