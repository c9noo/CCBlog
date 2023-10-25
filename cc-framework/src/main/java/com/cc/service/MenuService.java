package com.cc.service;

import com.cc.pojo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.vo.MenuVo;
import com.cc.pojo.vo.MenusVo;

import java.util.List;

/**
* @author 49751
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-10-21 15:47:49
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<MenuVo> treeSelect();

    MenusVo roleMenuTreeselect(Long id);
}
