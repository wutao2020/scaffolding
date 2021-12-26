package com.wt.adminvue.service;

import com.wt.adminvue.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
public interface IUserService extends IService<User> {
    User getByUsername(String username);
    String getUserAuthorityInfo(Long userId);
}
