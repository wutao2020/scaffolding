package com.wt.adminvue.controller.admin;


import com.wt.adminvue.dto.UserDto;
import com.wt.adminvue.entity.User;
import com.wt.adminvue.service.IUserService;
import com.wt.adminvue.util.Result;
import com.wt.adminvue.util.ResultGenerator;
import com.wt.adminvue.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wutao
 * @since 2021-12-26
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService service;
    @PostMapping("/userInfo")
    public Result<User> userInfo(){
        Long userId = UserUtil.getLoginUser().getUserId();
        User user = service.getById(userId);
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(UserDto dto, Integer current, Integer size) {
        if (current != null) {
            dto.setPageNo(current);
        }
        if (size != null) {
            dto.setPageSize(size);
        }
        return ResultGenerator.genSuccessResult(service.getList(dto));
    }
}
