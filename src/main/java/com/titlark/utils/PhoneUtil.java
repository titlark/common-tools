package com.titlark.utils;

import java.util.regex.Pattern;

/**
 * 手机号码工具类
 * @author lark
 */
public class PhoneUtil {

    // 手机号正则表达式
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 是否是中国大陆手机号码
     * @param phoneNumber 手机号码（以 13x、14x、15x、16x、17x、18x、19x 开头的 11 位数字）
     * @return true：合法的手机号码 false：不合法的手机号码
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.matches(PHONE_REGEX, phoneNumber);
    }

    /*public static String getPhoneLocation(String phoneNumber) {

    }*/
}
