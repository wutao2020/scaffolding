package com.wt.adminvue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wt.adminvue.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
@Mapper
@Resource
public interface UserMapper extends BaseMapper<User> {

    List<Long> getNavMenuIds(@Param("userId") Long userId);

    List<User> listByMenuId(@Param("menuId") Long menuId);
    Page<User> getList(Page<User> page, @Param("username") String username);
}
