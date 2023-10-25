package com.cc.mapper;

import com.cc.pojo.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2023-10-25 08:37:05
* @Entity com.cc.pojo.entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




