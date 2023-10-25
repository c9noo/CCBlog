package com.cc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.pojo.entity.UserRole;
import com.cc.service.UserRoleService;
import com.cc.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 49751
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-10-25 08:37:05
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




