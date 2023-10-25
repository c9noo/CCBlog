package com.cc.mapper;

import com.cc.pojo.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
* @createDate 2023-10-25 09:59:43
* @Entity com.cc.pojo.entity.RoleMenu
*/
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}




