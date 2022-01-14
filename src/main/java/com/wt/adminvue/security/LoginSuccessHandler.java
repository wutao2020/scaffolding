package com.wt.adminvue.security;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.wt.adminvue.model.JwtModel;
import com.wt.adminvue.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
/**
 * @description 认证成功处理
 * @author 吴涛
 * @date 2022-01-11 11:05
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private RedisUtil redisUtil;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		AccountUser principal = (AccountUser)authentication.getPrincipal();
		System.out.println("用户信息："+ principal.toString());
		String uuid = UUID.randomUUID().toString();
		redisUtil.hset(Const.ACCOUNTUSER,uuid, JSONObject.toJSONString(principal), jwtUtils.getExpire());
		// 生成jwt，并放置到请求头中
		String jwt = jwtUtils.generateToken(uuid);

		response.setHeader(jwtUtils.getHeader(), jwt);
		JwtModel model=new JwtModel();
		model.setJwt(jwt);
		Result result = ResultGenerator.genSuccessResult(model);
		outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
	}

}
