package com.wt.scaffolding.controller;


import com.wt.scaffolding.model.User;
import com.wt.scaffolding.service.INoticeService;
import com.wt.scaffolding.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
@RestController
@RequestMapping("/admin/notice")
public class NoticeController {
    @Autowired
    private INoticeService noticeService;
    @ApiOperation(value = "未读公告数")
    @GetMapping("/count-unread")
    public Integer countUnread() {
        User user = UserUtil.getLoginUser();
        return noticeService.countUnread(user.getId());
    }
}
