package com.wt.adminvue.controller.app;


import com.wt.adminvue.dto.TLoginDto;
import com.wt.adminvue.model.JwtModel;
import com.wt.adminvue.security.AccountUser;
import com.wt.adminvue.service.ITUserService;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultGenerator;
import com.wt.adminvue.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author wutao
 * @since 2022-01-14
 */
@RestController
@RequestMapping("/app/user")
public class TUserController {
    @Autowired
    private ITUserService service;
    @PostMapping("/loginByPassword")
    @ApiOperation("密码登录")
    public Result<JwtModel> loginByPassword(@RequestBody TLoginDto dto){
        JwtModel model=service.loginByPassword(dto);
        return ResultGenerator.genSuccessResult(model);
    }
    @PostMapping("/getuserInfo")
    @ApiOperation("获取用户信息")
    public Result<AccountUser> getuserInfo(){
        AccountUser loginUser = UserUtil.getLoginUser();
        return ResultGenerator.genSuccessResult(loginUser);
    }
}
