package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @program: CCBlog
 * @ClassName TagVo
 * @author: c9noo
 * @create: 2023-10-24 15:06
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TagDto {
    private Long id;
    private String name;
    private String remark;
}
