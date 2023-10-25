package com.cc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.pojo.entity.RoleMenu;
import com.cc.service.RoleMenuService;
import com.cc.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 49751
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-10-25 09:59:43
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




