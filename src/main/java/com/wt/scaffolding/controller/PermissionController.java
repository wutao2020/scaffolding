package com.wt.scaffolding.controller;


import com.wt.scaffolding.dto.LoginUser;
import com.wt.scaffolding.model.Permission;
import com.wt.scaffolding.service.IPermissionService;
import com.wt.scaffolding.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/admin/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @ApiOperation(value = "当前登录用户拥有的权限")
    @GetMapping("/current")
    public List<Permission> permissionsCurrent() {
        LoginUser loginUser = UserUtil.getLoginUser();
        List<Permission> list = loginUser.getPermissions();
        final List<Permission> permissions = list.stream().filter(l -> l.getType().equals(1))
                .collect(Collectors.toList());
        // 2018.06.09 支持多级菜单
        List<Permission> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(0)).collect(Collectors.toList());
        firstLevel.parallelStream().forEach(p -> {
            setChild(p, permissions);
        });
        return firstLevel;
    }
    /**
     * 设置子元素
     * 2018.06.09
     *
     * @param p
     * @param permissions
     */
    private void setChild(Permission p, List<Permission> permissions) {
        List<Permission> child = permissions.parallelStream().filter(a -> a.getParentId().equals(p.getId())).collect(Collectors.toList());
        p.setChild(child);
        if (!CollectionUtils.isEmpty(child)) {
            child.parallelStream().forEach(c -> {
                //递归设置子元素，多级菜单支持
                setChild(c, permissions);
            });
        }
    }
}
