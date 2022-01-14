package com.wt.adminvue.controller.admin;


import com.wt.adminvue.dto.LogsDto;
import com.wt.adminvue.dto.UserDto;
import com.wt.adminvue.service.ILogsService;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author wutao
 * @since 2021-12-31
 */
@RestController
@RequestMapping("/admin/logs")
public class LogsController {
    @Autowired
    private ILogsService service;
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(LogsDto dto, Integer current, Integer size) {
        if (current != null) {
            dto.setPageNo(current);
        }
        if (size != null) {
            dto.setPageSize(size);
        }
        return ResultGenerator.genSuccessResult(service.getList(dto));
    }

}
