package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.mapper.RoleMenuMapper;
import com.cc.pojo.dto.AddRole;
import com.cc.pojo.entity.Role;
import com.cc.pojo.entity.RoleMenu;
import com.cc.pojo.entity.UserRole;
import com.cc.pojo.vo.PageVo;
import com.cc.service.RoleMenuService;
import com.cc.service.RoleService;
import com.cc.mapper.RoleMapper;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 49751
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-10-21 15:48:49
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public PageVo selectList(Integer pageNum, Integer pageSize, String roleName, Integer status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(roleName)&& !roleName.isEmpty(),Role::getRoleName,roleName)
                .eq(Objects.nonNull(status),Role::getStatus,status);
        Page<Role> page = new Page<>(pageNum,pageSize);
        List<Role> roles = roleMapper.selectList(wrapper);
        return new PageVo(roles,page.getTotal());
    }

    @Override
    public void addRole(AddRole addRole) {
        Role role = BeanCopyUtils.copyBean(addRole, Role.class);
        roleMapper.insert(role);

        List<RoleMenu> roleMenus = addRole.getMenuIds().stream().map(
                menuId -> new RoleMenu(role.getId(), menuId)
        ).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);

    }
}




