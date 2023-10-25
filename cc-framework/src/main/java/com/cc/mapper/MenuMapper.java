package com.cc.mapper;

import com.cc.pojo.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 49751
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-10-21 15:47:49
* @Entity com.cc.pojo.entity.Menu
*/

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




