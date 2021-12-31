package com.wt.adminvue.service.impl;

import com.wt.adminvue.entity.Logs;
import com.wt.adminvue.mapper.LogsMapper;
import com.wt.adminvue.service.ILogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author wutao
 * @since 2021-12-31
 */
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {

}
