package com.wt.adminvue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.adminvue.dto.RolePageDto;
import com.wt.adminvue.entity.Role;
import com.wt.adminvue.mapper.RoleMapper;
import com.wt.adminvue.service.IRoleService;
import com.wt.adminvue.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    @Autowired
    private IUserService userService;
    @Override
    public List<Role> listRolesByUserId(Long id) {
        List<Role> sysRoles = this.list(new QueryWrapper<Role>()
                .inSql("id", "select role_id from sys_user_role where user_id = " + id));
        return sysRoles;
    }

    @Override
    public Page<Role> getList(RolePageDto dto) {
        Page<Role> page;
        if (dto.getPageNo() > 0 && dto.getPageSize() > 0) {
            page = new Page(dto.getPageNo(), dto.getPageSize());
        } else {
            page = new Page();
        }
        page=baseMapper.getList(page,dto.getName());
        return page;
    }

    @Override
    public Role getInfoById(Long id) {
        Role role = baseMapper.selectById(id);
        // 获取角色相关联的菜单id
        List<Long> menuIds=baseMapper.getMenuIds(id);
        role.setMenuIds(menuIds);
        return role;
    }

    @Override
    public Integer deleteRoleById(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);

        // 删除中间表
        baseMapper.deleteUserRoleById(ids);
        baseMapper.deleteRoleMenuById(ids);
        // 缓存同步删除
        ids.forEach(id -> {
            // 更新缓存
            userService.clearUserAuthorityInfoByRoleId(id);
        });
    }
}
