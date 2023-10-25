package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName TagListDto
 * @author: c9noo
 * @create: 2023-10-24 09:37
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    private String name;
    private String remark;
}
