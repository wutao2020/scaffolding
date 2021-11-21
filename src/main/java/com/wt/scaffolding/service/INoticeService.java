package com.wt.scaffolding.service;

import com.wt.scaffolding.model.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
public interface INoticeService extends IService<Notice> {

    Integer countUnread(Integer userId);
}
