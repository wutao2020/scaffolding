package com.wt.scaffolding.service.impl;

import com.wt.scaffolding.model.Logs;
import com.wt.scaffolding.mapper.LogsMapper;
import com.wt.scaffolding.service.ILogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {

}
