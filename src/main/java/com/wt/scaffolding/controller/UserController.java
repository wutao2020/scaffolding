package com.wt.scaffolding.controller;


import com.wt.scaffolding.model.User;
import com.wt.scaffolding.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public User currentUser() {
        return UserUtil.getLoginUser();
    }

}
