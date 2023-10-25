package com.cc.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @TableName cc_article
 */
@TableName(value ="cc_article")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Article implements Serializable {
    @TableId
    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private String isComment;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public Article(Long id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}