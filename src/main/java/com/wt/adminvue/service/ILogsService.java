package com.wt.adminvue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.dto.LogsDto;
import com.wt.adminvue.entity.Logs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日志 服务类
 * </p>
 *
 * @author wutao
 * @since 2021-12-31
 */
public interface ILogsService extends IService<Logs> {

    Page<Logs> getList(LogsDto dto);
}
