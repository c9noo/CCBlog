package com.cc.filter;

import com.alibaba.fastjson.JSON;
import com.cc.enums.AppHttpCodeEnum;
import com.cc.pojo.entity.LoginUser;
import com.cc.result.ResponseResult;
import com.cc.utils.JwtUtil;
import com.cc.utils.RedisCache;
import com.cc.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: CCBlog
 * @ClassName JwtAuthenticationTokenFilter
 * @author: c9noo
 * @create: 2023-10-17 19:59
 * @Version 1.0
 *
 * 在原先的security过滤链中，UsernamePasswordAuthenticationFilter会在验证成功之后创建Authentication对象并且返回给SecurityContextPersistenceFilter
 * 而SecurityContextPersistenceFilter负责创建并且在请求之间共享SecurityContext，
 * 它会在请求处理之前从存储介质中加载SecurityContext，并在请求处理后将其保存回存储介质
 *
 * 现在因为我们重写了login方法，所以我们需要自己创建一个过滤器去完成返回对象这个操作
 * 而OncePerRequestFilter是 Spring Security 提供的过滤器基类之一
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 获取到token
        String token = request.getHeader("token");
        if (token != null){
            //2. 从token中获取到userId
            Claims claims = null;
            try {
                claims = JwtUtil.parseJWT(token);
            } catch (Exception e) {
                //响应告诉前端需要重新登录
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            String userId = claims.getSubject();
            //3. 从redis中获取用户信息
            LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
            if (Objects.isNull(loginUser)){
                //说明登录过期  提示重新登录
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            //3. 添加到SecurityContextHold中
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginUser,
                            null,
                            null
                    );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);

    }
}
