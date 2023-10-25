package com.cc.handler.security;

import com.alibaba.fastjson.JSON;
import com.cc.enums.AppHttpCodeEnum;
import com.cc.result.ResponseResult;
import com.cc.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: CCBlog
 * @ClassName AccessDeniedHandlerImpl
 * @author: c9noo
 * @create: 2023-10-18 09:43
 * @Version 1.0
 * 已经登录，但是没有权限访问时
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
