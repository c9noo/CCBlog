package com.cc.handler.exception;

import com.cc.enums.AppHttpCodeEnum;
import com.cc.exception.SystemException;
import com.cc.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: CCBlog
 * @ClassName GlobalExceptionHandler
 * @author: c9noo
 * @create: 2023-10-18 14:26
 * @Version 1.0
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException systemException){
        log.error("出现了异常{}!",systemException);
        return ResponseResult.errorResult(systemException.getCode(),systemException.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }

}
