package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName MenuListVo
 * @author: c9noo
 * @create: 2023-10-25 14:28
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVo {
    private String component;
    private String icon;
    private Long id;
    private Integer isFrame;
    private String menuName;
    private String menuType;
    private Integer orderNum;
    private Long parentId;
    private String path;
    private String perms;
    private String remark;
    private String status;
    private String visible;
}
