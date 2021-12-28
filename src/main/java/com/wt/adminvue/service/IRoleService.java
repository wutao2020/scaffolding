package com.wt.adminvue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.dto.RolePageDto;
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

    Page<Role> getList(RolePageDto dto);

    Role getInfoById(Long id);

    Integer deleteRoleById(List<Long> ids);
}
