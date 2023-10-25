package com.cc.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: CCBlog
 * @ClassName ArticleDetailVo
 * @author: c9noo
 * @create: 2023-10-17 08:33
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {

    private Long id;

    private String title;

    private String content;

    private String isComment;

    private Long categoryId;

    private String categoryName;

    private Date createTime;

    private Long viewCount;

}
