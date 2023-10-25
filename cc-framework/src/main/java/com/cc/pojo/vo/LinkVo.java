package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName LinkVo
 * @author: c9noo
 * @create: 2023-10-17 08:59
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Long id;
    private String name;
    private String logo;
    private String description;
    private String address;
    private String status;
}
