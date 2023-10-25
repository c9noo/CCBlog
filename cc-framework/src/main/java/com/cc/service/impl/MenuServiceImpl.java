package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.constants.SystemConstants;
import com.cc.mapper.RoleMenuMapper;
import com.cc.pojo.entity.Menu;
import com.cc.pojo.entity.RoleMenu;
import com.cc.pojo.vo.MenuVo;
import com.cc.pojo.vo.MenusVo;
import com.cc.service.MenuService;
import com.cc.mapper.MenuMapper;
import com.cc.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 49751
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-10-21 15:47:49
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,"0");
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public List<MenuVo> treeSelect() {
        List<Menu> menuList = list(); // 从数据库中获取所有菜单数据
        List<MenuVo> menuTree = buildMenuTree(menuList, 0L); // 传入顶级菜单的 parentId
        return menuTree;
    }

    @Override
    public MenusVo roleMenuTreeselect(Long id) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);

        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        List<Long> collect = roleMenus.stream().map(
                RoleMenu::getMenuId
        ).collect(Collectors.toList());
        List<Menu> menus = menuMapper.selectBatchIds(collect);
        List<MenuVo> menuTree = buildMenuTree(menus, 0L);
        return new MenusVo(menuTree,collect);
    }

    public List<MenuVo> buildMenuTree(List<Menu> menuList, Long parentId) {
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    MenuVo menuVo = new MenuVo();
                    menuVo.setId(menu.getId());
                    menuVo.setLabel(menu.getMenuName());
                    menuVo.setParentId(menu.getParentId());
                    menuVo.setChildren(buildMenuTree(menuList, menu.getId()));
                    return menuVo;
                })
                .collect(Collectors.toList());
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




