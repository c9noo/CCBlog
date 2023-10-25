package com.cc.service;

import com.cc.pojo.entity.User;
import com.cc.result.ResponseResult;

/**
 * @program: CCBlog
 * @ClassName BlogLoginService
 * @author: c9noo
 * @create: 2023-10-17 18:57
 * @Version 1.0
 **/
public interface BlogLoginService {

    /**
     * 登录功能
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出功能
     * @return
     */
    ResponseResult logout();
}
