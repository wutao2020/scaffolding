package com.wt.adminvue.service;

import com.wt.adminvue.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wt.adminvue.entity.SysMenuModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
public interface IMenuService extends IService<Menu> {

    List<SysMenuModel> getCurrentUserNav();

    List<Menu> tree();


    Integer deleteMenu(Long id);
}
