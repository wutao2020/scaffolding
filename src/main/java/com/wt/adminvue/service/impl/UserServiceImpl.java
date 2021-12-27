package com.wt.adminvue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.dto.UserDto;
import com.wt.adminvue.entity.Menu;
import com.wt.adminvue.entity.Role;
import com.wt.adminvue.entity.User;
import com.wt.adminvue.mapper.UserMapper;
import com.wt.adminvue.service.IMenuService;
import com.wt.adminvue.service.IRoleService;
import com.wt.adminvue.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.adminvue.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IRoleService sysRoleService;


    @Autowired
    private IMenuService sysMenuService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        User sysUser = baseMapper.selectById(userId);
        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());

        } else {
            // 获取角色编码
            List<Role> roles = sysRoleService.list(new QueryWrapper<Role>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }
            // 获取菜单操作编码
            List<Long> menuIds = baseMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                Collection<Menu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
                authority = authority.concat(menuPerms);
            }
            redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authority, 60 * 60);
        }
        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<User> sysUsers = this.list(new QueryWrapper<User>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<User> sysUsers =baseMapper.listByMenuId(menuId);

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public Page<User> getList(UserDto dto) {
        Page<User> page;
        if (dto.getPageNo() > 0 && dto.getPageSize() > 0) {
            page = new Page(dto.getPageNo(), dto.getPageSize());
        } else {
            page = new Page();
        }
        page=baseMapper.getList(page,dto.getUsername());
        if (page!=null&&page.getRecords()!=null&&page.getRecords().size()>0){
            page.getRecords().forEach(u -> {
                u.setSysRoles(sysRoleService.listRolesByUserId(u.getId()));
            });
        }
        return page;
    }
}
