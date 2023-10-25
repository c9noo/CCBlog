package com.cc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.pojo.entity.ArticleTag;
import com.cc.service.ArticleTagService;
import com.cc.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author 49751
* @description 针对表【cc_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-10-24 15:33:32
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




