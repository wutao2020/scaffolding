package com.wt.adminvue.service;

import com.wt.adminvue.dto.TLoginDto;
import com.wt.adminvue.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wt.adminvue.model.JwtModel;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author wutao
 * @since 2022-01-14
 */
public interface ITUserService extends IService<TUser> {

    JwtModel loginByPassword(TLoginDto dto);
}
