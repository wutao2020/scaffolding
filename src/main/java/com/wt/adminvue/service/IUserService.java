package com.wt.adminvue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.dto.RolePermDto;
import com.wt.adminvue.dto.UserDto;
import com.wt.adminvue.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
public interface IUserService extends IService<User> {
    User getByUsername(String username);
    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);

    Page<User> getList(UserDto dto);

    Integer deleteUser(List<Long> ids);

    Integer rolePerm(RolePermDto dto);
}
