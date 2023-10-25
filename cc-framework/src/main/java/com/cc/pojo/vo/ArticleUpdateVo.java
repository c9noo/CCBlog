package com.cc.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: CCBlog
 * @ClassName ArticleUpdateVo
 * @author: c9noo
 * @create: 2023-10-24 19:05
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateVo {
    @TableId
    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private String isComment;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;

    private Integer delFlag;

    private List<Long> tags;

}
