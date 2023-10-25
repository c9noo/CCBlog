package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.constants.SystemConstants;
import com.cc.mapper.CategoryMapper;
import com.cc.pojo.dto.AddArticleDto;
import com.cc.pojo.entity.Article;
import com.cc.pojo.entity.ArticleTag;
import com.cc.pojo.entity.Category;
import com.cc.pojo.vo.*;
import com.cc.result.ResponseResult;
import com.cc.service.ArticleService;
import com.cc.mapper.ArticleMapper;
import com.cc.service.ArticleTagService;
import com.cc.utils.BeanCopyUtils;
import com.cc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 49751
* @description 针对表【cc_article(文章表)】的数据库操作Service实现
* @createDate 2023-10-16 10:59:03
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 热门文章
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        //1. 查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //2. 必须是正式文章，并且按照浏览量进行排序
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getViewCount);
        //3. 最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(page.getRecords(), HotArticleVo.class));
    }


    /**
     * 分页查询文章列表
     * @param pageSize
     * @param pageNum
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageSize, Integer pageNum, Long categoryId) {

        /**
         * 1.查询分页文章
         *  判断是否传入分类id，如果没传就查全部
         *  查询的需要是正式发布的
         *  对置顶进行降序
         */
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId)
                .eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getIsTop);

        //2. 分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        articleMapper.selectPage(page,lambdaQueryWrapper);
        List<Article> articleList = page.getRecords();

        //3. 取到分类id对应的分类名称并且赋值给article
        articleList.stream().map(article->
             article.setCategoryName(
                     categoryMapper.selectById(article.getCategoryId()
                     ).getName()
             )
        ).collect(Collectors.toList());

        //4. 将前端需要的数据封装成Vo 进行返回
        PageVo pageVo = new PageVo(
                BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class),
                page.getTotal()
        );
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //1. 查询文章详情
        Article article = getById(id);
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //2. 查询分类名称
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null){
            article.setCategoryName(category.getName());
        }
        //3. 封装成Vo，并且返回
        return ResponseResult.okResult(BeanCopyUtils.copyBean(article, ArticleDetailVo.class));
    }


    /**
     * 新增文章浏览量
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public PageVo selectList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(title)&& !title.isEmpty(),Article::getTitle,title);
        wrapper.eq(Objects.nonNull(summary)&& !summary.isEmpty(),Article::getSummary,summary);

        Page<Article> page = new Page<>(pageNum,pageSize);

        Page<Article> page1 = articleMapper.selectPage(page, wrapper);
        List<Article> records = page1.getRecords();
        return new PageVo(records,page.getTotal());
    }
}




