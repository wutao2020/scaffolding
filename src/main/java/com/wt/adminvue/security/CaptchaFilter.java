package com.wt.adminvue.security;

import cn.hutool.core.util.StrUtil;
import com.wt.adminvue.exception.CaptchaException;
import com.wt.adminvue.util.Const;
import com.wt.adminvue.util.RedisUtil;
import com.wt.adminvue.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @description 图形码验证
 * @author 吴涛
 * @date 2022-01-11 11:03
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private LoginFailureHandler loginFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		String url = httpServletRequest.getRequestURI();
		if ("/login".equals(url) && "POST".equals(httpServletRequest.getMethod())) {
			try{
				// 校验验证码
				validate(httpServletRequest);
			} catch (CaptchaException e) {
				System.out.println(e.getMessage());
				// 交给认证失败处理器
				loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	// 校验验证码逻辑
	private void validate(HttpServletRequest httpServletRequest) {
		String code = httpServletRequest.getParameter("code");
		String key = httpServletRequest.getParameter("randomCode");
		if (StrUtil.isBlank(code) || StrUtil.isBlank(key)) {
			throw new CaptchaException(ResultCode.COOE_NULL.getMsg());
		}
		System.out.println(code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key).toString()));
		if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key).toString())) {
			throw new CaptchaException(ResultCode.CODE_ERROR.getMsg());
		}
		// 一次性使用
		redisUtil.hdel(Const.CAPTCHA_KEY, key);
	}
}
