package com.cc.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName cc_category
 */
@TableName(value ="cc_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    @TableId
    private Long id;

    private String name;

    private Long pid;

    private String description;

    private String status;
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
}