package com.cc.pojo.vo;

import com.cc.pojo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName RoutersVo
 * @author: c9noo
 * @create: 2023-10-22 11:17
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;
}
