package com.wt.adminvue.util;

import org.springframework.util.StringUtils;

/**
 * 响应结果生成工具
 *
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link http://13blog.site
 */
public class ResultGenerator {

    public static Result genSuccessResult() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static Result genSuccessResult(String message) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }
    public static Result genSuccessResult(String message,String data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }
    public static Result genSuccessListResult(Object data, Long total) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        if (StringUtils.isEmpty(message)) {
            result.setMessage(ResultCode.ERROR.getMsg());
        } else {
            result.setMessage(message);
        }
        return result;
    }

    public static Result error(ResultCode result) {
        Result response = new Result();
        response.setCode(result.getCode());
        response.setMessage(result.getMsg());
        return response;
    }
    public static Result error(int code, String msg) {
        Result response = new Result();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }
}
