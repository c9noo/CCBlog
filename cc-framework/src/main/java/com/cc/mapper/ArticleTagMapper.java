package com.cc.mapper;

import com.cc.pojo.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【cc_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-10-24 15:33:32
* @Entity com.cc.pojo.entity.ArticleTag
*/
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}




