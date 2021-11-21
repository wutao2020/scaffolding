package com.wt.scaffolding.service.impl;


import com.wt.scaffolding.dto.LoginUser;
import com.wt.scaffolding.model.Permission;
import com.wt.scaffolding.model.Status;
import com.wt.scaffolding.model.User;
import com.wt.scaffolding.service.IPermissionService;
import com.wt.scaffolding.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spring security登陆处理<br>
 * <p>
 * 密码校验请看文档（02 框架及配置），第三章第4节
 *
 * @author 小威老师 xiaoweijiagou@163.com
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;
	@Autowired
	private IPermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User sysUser = userService.getUserByName(username);
		if (sysUser == null) {
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
		} else if (Status.LOCKED.equals(sysUser.getStatus())) {
			throw new LockedException("用户被锁定,请联系管理员");
		} else if (Status.DISABLED.equals(sysUser.getStatus())) {
			throw new DisabledException("用户已作废");
		}

		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(sysUser, loginUser);

		List<Permission> permissions = permissionService.selecePermissionByUserId(sysUser.getId());
		loginUser.setPermissions(permissions);

		return loginUser;
	}

}
