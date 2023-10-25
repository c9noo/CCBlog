package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName LinkDto
 * @author: c9noo
 * @create: 2023-10-24 21:11
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {
    private Long id;
    private String name;

    private String logo;

    private String description;

    private String address;

    private String status;

}
