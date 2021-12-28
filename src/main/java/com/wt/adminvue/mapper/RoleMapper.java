package com.wt.adminvue.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    Page<Role> getList(Page<Role> page,@Param("name") String name);

    List<Long> getMenuIds(@Param("roleId") Long id);
}
