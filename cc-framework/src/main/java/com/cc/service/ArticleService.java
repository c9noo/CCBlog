package com.cc.service;

import com.cc.pojo.dto.AddArticleDto;
import com.cc.pojo.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.vo.PageVo;
import com.cc.result.ResponseResult;

import java.util.List;

/**
* @author 49751
* @description 针对表【cc_article(文章表)】的数据库操作Service
* @createDate 2023-10-16 10:59:03
*/
public interface ArticleService extends IService<Article> {

    /**
     * 热门文章
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 分页查询文章列表
     * @param pageSize
     * @param pageNum
     * @param categoryId
     * @return
     */
    ResponseResult articleList(Integer pageSize, Integer pageNum, Long categoryId);

    /**
     * 文章详情
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 新增文章浏览量
     * @param id
     * @return
     */
    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);


    PageVo selectList(Integer pageNum, Integer pageSize, String title, String summary);
}
