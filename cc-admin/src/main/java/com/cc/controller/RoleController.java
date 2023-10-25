package com.cc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.pojo.dto.AddRole;
import com.cc.pojo.dto.RoleDto;
import com.cc.pojo.dto.RoleStatusDto;
import com.cc.pojo.entity.Role;
import com.cc.pojo.entity.RoleMenu;
import com.cc.pojo.vo.RoleInfoVo;
import com.cc.result.ResponseResult;
import com.cc.service.RoleMenuService;
import com.cc.service.RoleService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: CCBlog
 * @ClassName RoleController
 * @author: c9noo
 * @create: 2023-10-24 21:49
 * @Version 1.0
 **/
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("/listAllRole")
    public ResponseResult allRole(){
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus,0);
        List<Role> list = roleService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String roleName,Integer status){
        return ResponseResult.okResult(roleService.selectList(pageNum,pageSize,roleName,status));
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto){
        roleService.updateById(BeanCopyUtils.copyBean(roleStatusDto,Role.class));
        return ResponseResult.okResult();
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody AddRole addRole){
        roleService.addRole(addRole);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable Long id){
        Role role = roleService.getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(role, RoleInfoVo.class));
    }

    @PutMapping
    public ResponseResult putRole(@RequestBody RoleDto roleDto){
        roleService.updateById(BeanCopyUtils.copyBean(roleDto,Role.class));
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId,roleDto.getId()));
        List<RoleMenu> collect = roleDto.getMenuIds().stream().map(
                menuId -> new RoleMenu(roleDto.getId(), menuId)
        ).collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }


}
