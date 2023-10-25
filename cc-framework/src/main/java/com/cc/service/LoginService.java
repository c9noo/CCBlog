package com.cc.service;

import com.cc.pojo.entity.User;
import com.cc.result.ResponseResult;

/**
 * @program: CCBlog
 * @ClassName LoginService
 * @author: c9noo
 * @create: 2023-10-21 11:58
 * @Version 1.0
 **/
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
