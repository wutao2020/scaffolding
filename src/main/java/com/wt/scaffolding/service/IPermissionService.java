package com.wt.scaffolding.service;

import com.wt.scaffolding.model.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
public interface IPermissionService extends IService<Permission> {
  /**
   * @description 获取用户权限
   * @author 吴涛
   * @date 2021-11-18 15:35
   */
  List<Permission> selecePermissionByUserId(Integer userId);
}
