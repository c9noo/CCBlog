package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName MenusVo
 * @author: c9noo
 * @create: 2023-10-25 11:36
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenusVo {
    List<MenuVo> menus;
    List<Long> checkedKeys;
}
