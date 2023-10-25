package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName CategoryVo
 * @author: c9noo
 * @create: 2023-10-16 16:35
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {

    private Long id;
    private String name;
    //描述
    private String description;
    private String status;
}
