package com.titlark.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮箱工具类
 *
 * @author lark
 */
public class EmailUtil {

    // 定义邮箱的正则表达式
    private static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * 判断邮箱是否是合法的邮箱
     *
     * @param email 邮箱地址
     * @return true：合法的邮箱 false：不合法的邮箱
     */
    public static boolean isValidEmail(String email) {
        if (null == email) {
            return false;
        }
        // 编译正则表达式
        Pattern pattern = Pattern.compile(emailRegex);
        // 匹配邮箱
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
