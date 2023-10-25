package com.cc.service;

import com.cc.pojo.dto.AddRole;
import com.cc.pojo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.vo.PageVo;

import java.util.List;

/**
* @author 49751
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-10-21 15:48:49
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    PageVo selectList(Integer pageNum, Integer pageSize, String roleName, Integer status);

    void addRole(AddRole addRole);

}
