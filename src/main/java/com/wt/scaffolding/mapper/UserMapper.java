package com.wt.scaffolding.mapper;

import com.wt.scaffolding.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
