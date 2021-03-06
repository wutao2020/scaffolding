package com.wt.adminvue.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultCode;
import com.wt.adminvue.util.ResultGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
/**
 * @description 认证失败处理
 * @author 吴涛
 * @date 2022-01-11 11:01
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        Result result = null;
        if (StrUtil.isEmpty(exception.getMessage())){
            result=ResultGenerator.error(ResultCode.LOGIN_MOBILE_ERROR);
        }else {
          result=ResultGenerator.genFailResult(exception.getMessage());
        }
        System.out.println(result.toString());
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
