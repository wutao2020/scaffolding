package com.wt.scaffolding.service.impl;

import com.wt.scaffolding.model.Notice;
import com.wt.scaffolding.mapper.NoticeMapper;
import com.wt.scaffolding.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public Integer countUnread(Integer userId) {
        return baseMapper.countUnread(userId);
    }
}
