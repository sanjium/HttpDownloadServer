package com.download.exception;

import com.download.entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult handlerGlobalException(Exception e) {
        log.warn(e.getMessage(), e);
        return ResponseResult.error("系统错误,请联系管理员解决");
    }

}
