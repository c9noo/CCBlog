package com.cc.mapper;

import com.cc.pojo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 49751
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-10-21 15:48:49
* @Entity com.cc.pojo.entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}




