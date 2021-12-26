package com.wt.adminvue.util;

/**
 * 正则表达式匹配
 * @author tyg
 * @date   2018年9月21日下午4:32:27
 */
public class PatternUtil {

    /** 手机号码匹配 */
    public static final String PHONE_REG = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    /** 座机号码匹配 */
    public static final String MOBILE_REG = "^\\d{3}-\\d{8}|\\d{4}-\\d{7}$";
    /** 汉字匹配 */
    public static final String CHIINESE_REG = "^[\\u4e00-\\u9fa5]{0,}$";
    /** 英文和数字匹配(不区分大小写) */
    public static final String LETTER_NUMBER_REG = "^[A-Za-z0-9]+$";
    /** 非零开头的最多带两位小数的数字 */
    public static final String DECIMAL_REG = "^([1-9][0-9]*)+(.[0-9]{1,2})?$";
    /** 纯数字 */
    public static final String ONLY_NUMBER_REG = "^[0-9]{1,}$";
    /** 纯字母 */
    public static final String ONLY_LETTER_REG = "^[a-zA-Z]{1,}$";
    /** 邮箱email */
    public static final String EMAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /** 日期规则 */
    public static final String DATE_YMD = "^(\\d{4}-\\d{2}-\\d{2})$";
    /** 时间规则 */
    public static final String DATE_YMDHDS = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})$";
    /** 银行卡卡号位数 */
    public final static String BANK_CARD_NUMBER = "^\\d{16}|\\d{19}$";
    /** 身份证号码位数限制 */
    public final static String ID_CARD = "^\\d{15}|(\\d{17}[0-9,x,X])$";

    /**
     * 是否为手机号码
     * @param phone
     * @return
     * @return boolean
     * @author tyg
     * @date   2018年9月25日上午9:44:38
     */
    public static boolean isPhone(String phone) {
        return phone.matches(PHONE_REG);
    }

    /**
     * 是否为座机号码
     * @param mobile
     * @return
     * @return boolean
     * @author tyg
     * @date   2018年9月25日上午9:44:38
     */
    public static boolean isMobile(String mobile) {
        return mobile.matches(MOBILE_REG);
    }

    public static void main(String[] args) {
        System.out.println(isPhone("13228116626"));
        System.out.println(isMobile("028-63358547"));
    }
}
