package com.cc.mapper;

import com.cc.pojo.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【cc_article(文章表)】的数据库操作Mapper
* @createDate 2023-10-16 10:59:03
* @Entity com.cc.entity.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




