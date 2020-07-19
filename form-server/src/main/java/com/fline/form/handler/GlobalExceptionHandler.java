package com.fline.form.handler;

import com.fline.form.exception.BaseException;
import com.fline.form.vo.ResponseResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BaseException.class)
    public ResponseResult<String> baseExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e.getCause() == null ? e : e.getCause());
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseResult<String> otherExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseResult.error("操作失败！");
    }


}
