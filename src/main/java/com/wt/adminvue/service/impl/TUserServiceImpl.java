package com.wt.adminvue.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wt.adminvue.dto.TLoginDto;
import com.wt.adminvue.entity.TUser;
import com.wt.adminvue.exception.GuliException;
import com.wt.adminvue.mapper.TUserMapper;
import com.wt.adminvue.model.JwtModel;
import com.wt.adminvue.security.AccountUser;
import com.wt.adminvue.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.adminvue.util.Const;
import com.wt.adminvue.util.JwtUtils;
import com.wt.adminvue.util.RedisUtil;
import com.wt.adminvue.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author wutao
 * @since 2022-01-14
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public JwtModel loginByPassword(TLoginDto dto) {
        if (StrUtil.isEmpty(dto.getPassword())||StrUtil.isEmpty(dto.getPetName())){
            throw new GuliException(ResultCode.PARAM_ERROR);
        }
        QueryWrapper<TUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pet_name", dto.getPetName());
        TUser tUser = baseMapper.selectOne(queryWrapper);
        if (tUser==null){
            throw new GuliException(ResultCode.LOGIN_PHONE_ERROR);
        }
        System.out.println("原密码:"+dto.getPassword());
        System.out.println("密码:"+bCryptPasswordEncoder.encode(dto.getPassword()));
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), tUser.getPassword())){
            throw new GuliException(ResultCode.LOGIN_PASSWORD_ERROR);
        }
        AccountUser user = new AccountUser(tUser.getId(), tUser.getPetName(), tUser.getPassword(), 0, null);
        String uuid = UUID.randomUUID().toString();
        redisUtil.hset(Const.ACCOUNTUSER,uuid, JSONObject.toJSONString(user), jwtUtils.getExpire());
        // 生成jwt，并放置到请求头中
        String jwt = jwtUtils.generateToken(uuid);
        JwtModel model=new JwtModel();
        model.setJwt(jwt);
        return model;
    }
}
