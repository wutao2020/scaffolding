package com.wt.adminvue.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author qy
 * @since 1.0
 */
public class FormUtils {

    /**
     * 手机号验证
     */
    private static Pattern NUMBER_PATTERN = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
    public static boolean isMobile(String str) {
        Matcher m = NUMBER_PATTERN.matcher(str);
        return m.matches();
    }

}
