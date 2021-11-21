package com.wt.scaffolding.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.scaffolding.dto.LoginUser;
import com.wt.scaffolding.mapper.TokenMapper;
import com.wt.scaffolding.model.Logs;
import com.wt.scaffolding.model.Token;
import com.wt.scaffolding.model.TokenModel;
import com.wt.scaffolding.service.ILogsService;
import com.wt.scaffolding.service.ITokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-18
 */
@Service
@Slf4j
public class TokenServiceImpl extends ServiceImpl<TokenMapper, TokenModel> implements ITokenService {

    /**
     * token过期秒数
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;

    @Autowired
    private ILogsService logService;
    /**
     * 私钥
     */
    @Value("${token.jwtSecret}")
    private String jwtSecret;

    private static Key KEY = null;
    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Override
    public Token saveToken(LoginUser loginUser) {
        loginUser.setToken(UUID.randomUUID().toString());
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);

        TokenModel model = new TokenModel();
        model.setId(loginUser.getToken());
        model.setCreateTime(LocalDateTime.now());
        model.setUpdateTime(LocalDateTime.now());
        model.setExpireTime(LocalDateTimeUtil.of(loginUser.getExpireTime()));
        model.setVal(JSONObject.toJSONString(loginUser));

        baseMapper.insert(model);
        // 登陆日志
        Logs logs=new Logs();
        logs.setUserId(loginUser.getId());
        logs.setModule("登录");
        logs.setFlag(1);
        logs.setCreateTime(LocalDateTime.now());
        logService.save(logs);

        String jwtToken = createJWTToken(loginUser);

        return new Token(jwtToken, loginUser.getLoginTime());
    }

    /**
     * 生成jwt
     *
     * @param loginUser
     * @return
     */
    private String createJWTToken(LoginUser loginUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return jwtToken;
    }

    @Override
    public void refresh(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
        TokenModel model = baseMapper.selectById(loginUser.getToken());
        model.setUpdateTime(LocalDateTimeUtil.now());
        model.setExpireTime(LocalDateTimeUtil.of(loginUser.getExpireTime()));
        model.setVal(JSONObject.toJSONString(loginUser));
        baseMapper.updateById(model);
    }

    @Override
    public LoginUser getLoginUser(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            TokenModel model = baseMapper.selectById(uuid);
            return toLoginUser(model);
        }

        return null;
    }

    @Override
    public boolean deleteToken(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            TokenModel model = baseMapper.selectById(uuid);
            LoginUser loginUser = toLoginUser(model);
            if (loginUser != null) {
                baseMapper.deleteById(uuid);
                // 登陆日志
                Logs logs=new Logs();
                logs.setUserId(loginUser.getId());
                logs.setModule("退出");
                logs.setFlag(1);
                logs.setCreateTime(LocalDateTime.now());
                logService.save(logs);
                return true;
            }
        }

        return false;
    }

    private LoginUser toLoginUser(TokenModel model) {
        if (model == null) {
            return null;
        }

        // 校验是否已过期
        if (model.getExpireTime().toInstant(ZoneOffset.of("+8")).toEpochMilli() > System.currentTimeMillis()) {
            return JSONObject.parseObject(model.getVal(), LoginUser.class);
        }

        return null;
    }

    private Key getKeyInstance() {
        if (KEY == null) {
            synchronized (TokenServiceImpl.class) {
                if (KEY == null) {// 双重锁
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }

        return KEY;
    }

    private String getUUIDFromJWT(String jwt) {
        if ("null".equals(jwt) || StringUtils.isBlank(jwt)) {
            return null;
        }
        Map<String, Object> jwtClaims = null;
        try {
            jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
        } catch (ExpiredJwtException e) {
            log.error("{}已过期", jwt);
        } catch (Exception e) {
            log.error("{}", e);
        }

        return null;
    }

}
