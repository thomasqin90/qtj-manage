package com.qtj.manageserver.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> error(Exception e) {
        log.error("系统异常", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<String> error(CustomException e) {
        log.error("自定义异常", e);
        return Result.error(e.getCode(), e.getMessage());
    }
}
