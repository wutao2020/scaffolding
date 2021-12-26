package com.wt.adminvue.advice;


import com.wt.adminvue.exception.CaptchaException;
import com.wt.adminvue.exception.GuliException;
import com.wt.adminvue.util.ExceptionUtils;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultCode;
import com.wt.adminvue.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return ResultGenerator.error(ResultCode.ERROR);
    }
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public Result error(BadSqlGrammarException e){
        log.error(ExceptionUtils.getMessage(e));
        return ResultGenerator.error(ResultCode.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result error(HttpMessageNotReadableException e){
        log.error(ExceptionUtils.getMessage(e));
        return ResultGenerator.error(ResultCode.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e){
        log.error(ExceptionUtils.getMessage(e));
        return ResultGenerator.error(e.getCode(),e.getMessage());
    }
    @ExceptionHandler(CaptchaException.class)
    @ResponseBody
    public Result error(CaptchaException e){
        log.error(ExceptionUtils.getMessage(e));
        return ResultGenerator.error(e.hashCode(),e.getMessage());
    }
}