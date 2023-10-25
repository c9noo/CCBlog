package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName MenuVo
 * @author: c9noo
 * @create: 2023-10-25 10:02
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class MenuVo {
    private Long id;
    private String label;
    private Long parentId;
    List<MenuVo> children;
}
