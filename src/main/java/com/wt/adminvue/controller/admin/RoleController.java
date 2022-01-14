package com.wt.adminvue.controller.admin;


import com.wt.adminvue.annotation.LogAnnotation;
import com.wt.adminvue.dto.PermDto;
import com.wt.adminvue.dto.RoleDto;
import com.wt.adminvue.dto.RolePageDto;
import com.wt.adminvue.entity.Role;
import com.wt.adminvue.service.IRoleService;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.Const;
import com.wt.adminvue.util.OprLogConst;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    private IRoleService service;
    @Autowired
    private IUserService userService;
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result list(RolePageDto dto, Integer current, Integer size) {
        if (current != null) {
            dto.setPageNo(current);
        }
        if (size != null) {
            dto.setPageSize(size);
        }
        return ResultGenerator.genSuccessResult(service.getList(dto));
    }
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {

        Role sysRole = service.getInfoById(id);
        return ResultGenerator.genSuccessResult(sysRole);
    }
    @PostMapping("/save")
    @LogAnnotation(module = "角色模块",operType = OprLogConst.ADD,operDesc = "角色保存")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result save(@Validated @RequestBody RoleDto dto) {
        Role sysRole=new Role();
        BeanUtils.copyProperties(dto, sysRole);
        sysRole.setCreated(LocalDateTime.now());
        if (dto!=null&&dto.getStatu()==null){
            sysRole.setStatu(Const.STATUS_ON);
        }
        return ResultGenerator.genSuccessResult(service.save(sysRole));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    @LogAnnotation(module = "角色模块",operType = OprLogConst.UPDATE,operDesc = "角色修改")
    public Result update(@Validated @RequestBody RoleDto dto) {
        Role sysRole=new Role();
        BeanUtils.copyProperties(dto, sysRole);
        sysRole.setUpdated(LocalDateTime.now());
        service.updateById(sysRole);

        // 更新缓存
        userService.clearUserAuthorityInfoByRoleId(sysRole.getId());

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional
    @LogAnnotation(module = "角色模块",operType = OprLogConst.DELETE,operDesc = "角色删除")
    public Result deleteRoleById(@RequestBody List<Long> ids) {

        return ResultGenerator.genSuccessResult(service.deleteRoleById(ids));
    }

    @Transactional
    @PostMapping("/perm")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    @LogAnnotation(module = "角色模块",operType = OprLogConst.UPDATE,operDesc = "角色分配权限")
    public Result perm(@RequestBody PermDto dto) {
        return ResultGenerator.genSuccessResult(service.perm(dto));
    }


}
