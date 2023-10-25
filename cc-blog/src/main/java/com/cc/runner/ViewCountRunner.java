package com.cc.runner;

import com.cc.mapper.ArticleMapper;
import com.cc.pojo.entity.Article;
import com.cc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: CCBlog
 * @ClassName ViewCountRunner
 * @author: c9noo
 * @create: 2023-10-20 18:51
 * @Version 1.0
 **/

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 读取数据库文章的浏览量 写入到redis中
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();//
                }));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
