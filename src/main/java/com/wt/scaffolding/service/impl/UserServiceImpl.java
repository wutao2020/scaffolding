package com.wt.scaffolding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wt.scaffolding.model.User;
import com.wt.scaffolding.mapper.UserMapper;
import com.wt.scaffolding.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return baseMapper.selectOne(queryWrapper);
    }
}
