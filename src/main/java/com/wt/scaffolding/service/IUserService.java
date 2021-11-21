package com.wt.scaffolding.service;

import com.wt.scaffolding.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
public interface IUserService extends IService<User> {

    User getUserByName(String username);
}
