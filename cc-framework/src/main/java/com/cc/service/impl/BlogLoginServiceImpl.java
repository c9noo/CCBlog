package com.cc.service.impl;

import com.cc.pojo.entity.LoginUser;
import com.cc.pojo.entity.User;
import com.cc.pojo.vo.BlogUserLoginVo;
import com.cc.pojo.vo.UserInfoVo;
import com.cc.result.ResponseResult;
import com.cc.service.BlogLoginService;
import com.cc.utils.BeanCopyUtils;
import com.cc.utils.JwtUtil;
import com.cc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: CCBlog
 * @ClassName BlogLoginServiceImpl
 * @author: c9noo
 * @create: 2023-10-17 18:59
 * @Version 1.0
 * 因为重写了security的login方法，所以我们需要自己创建一个authenticationManager对象并且调用authenticate方法
 **/
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
        //5. 封装返回
        //5.1 将user对象转换成userInfoVo对象
        return ResponseResult.okResult(new BlogUserLoginVo(jwt,BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class)));
    }

    /**
     * 退出功能
     * @return
     */
    @Override
    public ResponseResult logout() {
        //1. 获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        //2. 清除redis
        redisCache.deleteObject("bloglogin:"+user.getId());
        return ResponseResult.okResult();
    }
}
