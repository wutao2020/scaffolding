package com.wt.adminvue.service;

import com.wt.adminvue.entity.Role;
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
public interface IRoleService extends IService<Role> {
    List<Role> listRolesByUserId(Long id);
}
