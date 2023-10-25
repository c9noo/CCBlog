package com.cc.controller;

import com.cc.enums.AppHttpCodeEnum;
import com.cc.exception.SystemException;
import com.cc.pojo.entity.User;
import com.cc.result.ResponseResult;
import com.cc.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @program: CCBlog
 * @ClassName BlogLoginController
 * @author: c9noo
 * @create: 2023-10-17 18:55
 * @Version 1.0
 * 重写security的login
 **/
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    /**
     * 登录功能
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    /**
     * 退出功能
     * @return
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
