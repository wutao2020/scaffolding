package com.wt.adminvue.security;

import com.wt.adminvue.entity.User;
import com.wt.adminvue.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
    private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User sysUser = userService.getByUsername(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(Long userId){

		// 角色(ROLE_admin)、菜单操作权限 sys:user:list
		String authority = userService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,sys:user:list,....

		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
	}
}
