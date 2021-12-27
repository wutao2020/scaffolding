package com.wt.adminvue.mapper;

import com.wt.adminvue.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
public interface MenuMapper extends BaseMapper<Menu> {

    Integer removeMenuRoleById(@Param("id") Long id);
}
