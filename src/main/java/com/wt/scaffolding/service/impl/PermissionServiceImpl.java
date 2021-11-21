package com.wt.scaffolding.service.impl;

import com.wt.scaffolding.model.Permission;
import com.wt.scaffolding.mapper.PermissionMapper;
import com.wt.scaffolding.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public List<Permission> selecePermissionByUserId(Integer userId) {
        return baseMapper.selecePermissionByUserId(userId);
    }
}
