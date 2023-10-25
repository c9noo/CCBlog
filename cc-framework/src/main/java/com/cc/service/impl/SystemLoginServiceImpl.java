package com.cc.service.impl;

import com.cc.pojo.entity.LoginUser;
import com.cc.pojo.entity.User;
import com.cc.result.ResponseResult;
import com.cc.service.LoginService;
import com.cc.utils.JwtUtil;
import com.cc.utils.RedisCache;
import com.cc.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: CCBlog
 * @ClassName SystemLoginServiceImpl
 * @author: c9noo
 * @create: 2023-10-21 11:58
 * @Version 1.0
 **/
@Service
public class SystemLoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录功能
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {
        //1. 将账号密码封装成一个UsernamePasswordAuthenticationToken对象，并且调用authenticate 进行认证
        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUserName(), user.getPassword()
                        )
                );
        //2. 判断认证结果，如果为空代表认证失败
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("账号或密码错误");
        }
        //3. 认证成功，取出用户id，进行生成Jwt
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //4. 把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //5. 封装返回
        //5.1 将user对象转换成userInfoVo对象
        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
