package com.wt.adminvue.mapper;

import com.wt.adminvue.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
public interface UserMapper extends BaseMapper<User> {

    List<Long> getNavMenuIds(@Param("userId") Long userId);
}
