package com.wt.adminvue.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.entity.Logs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 日志 Mapper 接口
 * </p>
 *
 * @author wutao
 * @since 2021-12-31
 */
public interface LogsMapper extends BaseMapper<Logs> {

    Page<Logs> getLogsPage(Page<Logs> page, @Param("operUserName") String operUserName, @Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime , @Param("operType") String operType, @Param("isAbnormal") Integer isAbnormal, @Param("isManage") Integer isManage);
}
