package com.wt.scaffolding.service;

import com.wt.scaffolding.dto.LoginUser;
import com.wt.scaffolding.model.Token;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wt.scaffolding.model.TokenModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
public interface ITokenService extends IService<TokenModel> {

    Token saveToken(LoginUser loginUser);
    void refresh(LoginUser loginUser);

    LoginUser getLoginUser(String token);

    boolean deleteToken(String token);

}
