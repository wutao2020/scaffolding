package com.wt.adminvue.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.dto.LogsDto;
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

    @Override
    public Page<Logs> getList(LogsDto dto) {

        Page<Logs> page;
        if (dto.getPageNo() > 0 && dto.getPageSize() > 0) {
            page = new Page(dto.getPageNo(), dto.getPageSize());
        } else {
            page = new Page();
        }
        DateTime startTime = null;
        DateTime endTime=null;
        if (!StrUtil.isEmpty(dto.getCreateTime())){
            DateTime disasterTime = DateUtil.parse(dto.getCreateTime(), "yyyy-MM-dd");
            startTime= DateUtil.beginOfDay(disasterTime);
            endTime=DateUtil.endOfDay(disasterTime);
        }
        page=baseMapper.getLogsPage(page,dto.getOperUserName(),startTime,endTime,dto.getOperType(),dto.getIsAbnormal(),dto.getIsManage());
        return page;
    }
}
