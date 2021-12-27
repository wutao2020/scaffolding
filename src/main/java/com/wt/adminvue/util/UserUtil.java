package com.wt.adminvue.util;

import com.wt.adminvue.security.AccountUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName UserUtil.java
 * @Description TODO
 * @createTime 2021年12月27日 16:06:00
 */
public class UserUtil {
    public static AccountUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return new AccountUser();
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return (AccountUser) authentication.getPrincipal();
            }
        }
        return new AccountUser();
    }
}
