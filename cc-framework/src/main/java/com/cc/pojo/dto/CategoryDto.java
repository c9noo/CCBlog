package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName CategoryDto
 * @author: c9noo
 * @create: 2023-10-24 16:26
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String status;
}
