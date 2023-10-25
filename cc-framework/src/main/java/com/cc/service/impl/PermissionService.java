package com.cc.service.impl;

import com.cc.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName PermissionService
 * @author: c9noo
 * @create: 2023-10-24 18:30
 * @Version 1.0
 **/
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission){
        //如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
