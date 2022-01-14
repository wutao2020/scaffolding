package com.wt.adminvue.security;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wt.adminvue.exception.GuliException;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.Const;
import com.wt.adminvue.util.JwtUtils;
import com.wt.adminvue.util.RedisUtil;
import com.wt.adminvue.util.ResultCode;
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
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Autowired
    IUserService sysUserService;
	@Autowired
	private RedisUtil redisUtil;
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
//		User sysUser = sysUserService.getByUsername(username);
		Object o = redisUtil.hget(Const.ACCOUNTUSER,username);
		if (o!=null){
			JSONObject jsonObject = JSONObject.parseObject(o.toString());
			JSONArray authorities = jsonObject.getJSONArray("authorities");
			List<GrantedAuthority> authorityList=new ArrayList<>();
			if (authorities!=null&&authorities.size()>0){
				authorities.forEach(authoritie->{
					GrantedAuthority grantedAuthority=new GrantedAuthority() {
						@Override
						public String getAuthority() {
							return JSONObject.parseObject(authoritie.toString()).getString("authority");
						}
					};
					authorityList.add(grantedAuthority);
				});
			}
			AccountUser accountUser = new AccountUser(jsonObject.getLong("userId"), jsonObject.getString("username"),jsonObject.getString("password"),jsonObject.getInteger("isAdmin"), authorityList);
			UsernamePasswordAuthenticationToken token
					= new UsernamePasswordAuthenticationToken(accountUser, null, authorityList);
			SecurityContextHolder.getContext().setAuthentication(token);
			chain.doFilter(request, response);
		}else {
			throw new GuliException(ResultCode.LOGIN_AUTH);
		}
	}
}
