package com.wt.adminvue.controller.admin;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wt.adminvue.dto.MenuDto;
import com.wt.adminvue.entity.Menu;
import com.wt.adminvue.entity.NavModel;
import com.wt.adminvue.entity.SysMenuModel;
import com.wt.adminvue.security.AccountUser;
import com.wt.adminvue.service.IMenuService;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultCode;
import com.wt.adminvue.util.ResultGenerator;
import com.wt.adminvue.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
@RestController
@RequestMapping("admin/menu")
public class MenuController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    /**
     * 用户当前用户的菜单和权限信息
     *
     * @return
     */
    @GetMapping("/nav")
    public Result<NavModel> nav() {
        AccountUser loginUser = UserUtil.getLoginUser();
        NavModel model=new NavModel();
        // 获取权限信息
        String authorityInfo = userService.getUserAuthorityInfo(loginUser.getUserId());// ROLE_admin,ROLE_normal,sys:user:list,....
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");
        List<String> strings = StrUtil.split(authorityInfo, ",");
        model.setAuthoritys(strings);
        // 获取导航栏信息
        List<SysMenuModel> navs = menuService.getCurrentUserNav();
        model.setNav(navs);
        return ResultGenerator.genSuccessResult(model);
    }
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<Menu>> list() {

        List<Menu> menus = menuService.tree();
        return ResultGenerator.genSuccessResult(menus);
    }
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<Menu> info(@PathVariable("id")Integer id){
        return ResultGenerator.genSuccessResult(menuService.getById(id));
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<Integer> save(@RequestBody MenuDto dto){
        Menu menu=new Menu();
        BeanUtils.copyProperties(dto, menu);
        menu.setCreated(LocalDateTime.now());
        menu.setUpdated(LocalDateTime.now());
        return ResultGenerator.genSuccessResult(menuService.save(menu));
    }
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<Integer> update(@RequestBody MenuDto dto){
        Menu menu=new Menu();
        BeanUtils.copyProperties(dto, menu);
        menu.setUpdated(LocalDateTime.now());
        // 清除所有与该菜单相关的权限缓存
        userService.clearUserAuthorityInfoByMenuId(menu.getId());
        return ResultGenerator.genSuccessResult(menuService.updateById(menu));
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result delete(@PathVariable("id") Long id) {
        return ResultGenerator.genSuccessResult(menuService.deleteMenu(id));
    }
}
