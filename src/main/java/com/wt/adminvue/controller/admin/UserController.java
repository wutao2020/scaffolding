package com.wt.adminvue.controller.admin;


import cn.hutool.core.util.StrUtil;
import com.wt.adminvue.annotation.LogAnnotation;
import com.wt.adminvue.dto.PassDto;
import com.wt.adminvue.dto.RolePermDto;
import com.wt.adminvue.dto.SaveUserDto;
import com.wt.adminvue.dto.UserDto;
import com.wt.adminvue.entity.Role;
import com.wt.adminvue.entity.User;
import com.wt.adminvue.service.IRoleService;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService service;
    @Autowired
    private IRoleService roleService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/userInfo")
    public Result<User> userInfo(){
        Long userId = UserUtil.getLoginUser().getUserId();
        User user = service.getById(userId);
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(UserDto dto, Integer current, Integer size) {
        if (current != null) {
            dto.setPageNo(current);
        }
        if (size != null) {
            dto.setPageSize(size);
        }
        return ResultGenerator.genSuccessResult(service.getList(dto));
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result info(@PathVariable("id") Long id) {
        User sysUser = service.getById(id);
        if (sysUser==null){
            return ResultGenerator.genSuccessResult(ResultCode.NOT_USER);
        }

        List<Role> roles = roleService.listRolesByUserId(id);

        sysUser.setSysRoles(roles);
        return ResultGenerator.genSuccessResult(sysUser);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    @LogAnnotation(module = "管理员模块",operType = OprLogConst.ADD,operDesc = "管理员新增")
    public Result save(@Validated @RequestBody SaveUserDto dto) {
        User sysUser=new User();
        BeanUtils.copyProperties(dto, sysUser);
        sysUser.setCreated(LocalDateTime.now());
        if (sysUser.getStatu()==null){
            sysUser.setStatu(Const.STATUS_ON);
        }

        // 默认密码
        String password = passwordEncoder.encode(Const.DEFULT_PASSWORD);
        sysUser.setPassword(password);

        if (StrUtil.isEmpty(sysUser.getAvatar())){
            // 默认头像
            sysUser.setAvatar(Const.DEFULT_AVATAR);
        }

        service.save(sysUser);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @LogAnnotation(module = "管理员模块",operType = OprLogConst.UPDATE,operDesc = "管理员修改")
    public Result update(@Validated @RequestBody SaveUserDto dto) {
        User sysUser=new User();
        BeanUtils.copyProperties(dto, sysUser);
        sysUser.setUpdated(LocalDateTime.now());

        service.updateById(sysUser);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @LogAnnotation(module = "管理员模块",operType = OprLogConst.UPDATE,operDesc = "管理员删除")
    public Result delete(@RequestBody List<Long> ids) {
        return ResultGenerator.genSuccessResult(service.deleteUser(ids));
    }


    @PostMapping("/rolePerm")
    @PreAuthorize("hasAuthority('sys:user:role')")
    @LogAnnotation(module = "管理员模块",operType = OprLogConst.UPDATE,operDesc = "管理员分配角色")
    public Result rolePerm(@RequestBody RolePermDto dto) {
        return ResultGenerator.genSuccessResult(service.rolePerm(dto));
    }

    @PostMapping("/repass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    @LogAnnotation(module = "管理员模块",operType = OprLogConst.UPDATE,operDesc = "管理员重置密码")
    public Result repass(@RequestBody Long userId) {

        User sysUser = service.getById(userId);

        sysUser.setPassword(passwordEncoder.encode(Const.DEFULT_PASSWORD));
        sysUser.setUpdated(LocalDateTime.now());

        service.updateById(sysUser);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/updatePass")
    @LogAnnotation(module = "管理员模块",operType = OprLogConst.UPDATE,operDesc = "管理员修改密码")
    public Result updatePass(@Validated @RequestBody PassDto passDto) {
        Long userId = UserUtil.getLoginUser().getUserId();
        User sysUser = service.getById(userId);

        boolean matches = passwordEncoder.matches(passDto.getCurrentPass(), sysUser.getPassword());
        if (!matches) {
            return ResultGenerator.genFailResult("旧密码不正确");
        }

        sysUser.setPassword(passwordEncoder.encode(passDto.getPassword()));
        sysUser.setUpdated(LocalDateTime.now());

        service.updateById(sysUser);
        return ResultGenerator.genSuccessResult();
    }
}
