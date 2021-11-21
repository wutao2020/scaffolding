package com.wt.scaffolding.mapper;

import com.wt.scaffolding.model.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    Integer countUnread(Integer userId);
}
