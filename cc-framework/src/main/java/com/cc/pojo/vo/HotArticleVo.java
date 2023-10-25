package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName HotArticleVo
 * @author: c9noo
 * @create: 2023-10-16 13:42
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    /**
     * 文章id
     */
    private Long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章访问量
     */
    private Long viewCount;
}
