package com.wt.scaffolding.mapper;

import com.wt.scaffolding.model.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selecePermissionByUserId(@Param("userId") Integer userId);
}
