package com.wt.adminvue.security;

import cn.hutool.core.util.StrUtil;
import com.wt.adminvue.entity.User;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Autowired
    IUserService sysUserService;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		String jwt = request.getHeader(jwtUtils.getHeader());
		if (StrUtil.isBlankOrUndefined(jwt)) {
			chain.doFilter(request, response);
			return;
		}

		Claims claim = jwtUtils.getClaimByToken(jwt);
		if (claim == null) {
			throw new JwtException("token 异常");
		}
		if (jwtUtils.isTokenExpired(claim)) {
			throw new JwtException("token已过期");
		}

		String username = claim.getSubject();
		// 获取用户的权限等信息
		User sysUser = sysUserService.getByUsername(username);
		List<GrantedAuthority> userAuthority = userDetailService.getUserAuthority(sysUser.getId());
		AccountUser accountUser = new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), userAuthority);
		UsernamePasswordAuthenticationToken token
				= new UsernamePasswordAuthenticationToken(accountUser, null, userAuthority);

		SecurityContextHolder.getContext().setAuthentication(token);

		chain.doFilter(request, response);
	}
}
