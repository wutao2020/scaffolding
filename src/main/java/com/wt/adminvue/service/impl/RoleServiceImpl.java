package com.wt.adminvue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wt.adminvue.entity.Role;
import com.wt.adminvue.mapper.RoleMapper;
import com.wt.adminvue.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public List<Role> listRolesByUserId(Long id) {
        List<Role> sysRoles = this.list(new QueryWrapper<Role>()
                .inSql("id", "select role_id from sys_user_role where user_id = " + id));
        return sysRoles;
    }
}
