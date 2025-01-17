package com.titlark.utils;

import java.util.Collection;

/**
 * 字符串工具类
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * 判断字符串是否为空
     *
     * @param cs 待校验的字符串
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param cs 待校验的字符串
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 是否有一个为空，有一个为空就为true
     *
     * @param cs
     * @return
     */
    public static boolean isAnyEmpty(CharSequence... cs) {
        if (null == cs) return true;
        for (CharSequence c : cs) {
            if (null == c) return true;
        }
        return false;
    }

    /**
     * 所有值都不为空，则返回true，否则返回false
     *
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(CharSequence... cs) {
        return !isAnyEmpty(cs);
    }

    /**
     * 是否为真空值(空格或者空值)
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        if (isEmpty(cs)) return true;
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否真的不为空,不是空格或者空值
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 是否包含任何真空值(包含空格或空值)
     *
     * @param cs
     * @return
     */
    public static boolean isAnyBlank(CharSequence... cs) {
        if (null == cs) return true;
        for (CharSequence c : cs) {
            if (isBlank(c)) return true;
        }
        return false;
    }

    /**
     * 是否全部都不包含空值或空格
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(CharSequence... cs) {
        return !isAnyBlank(cs);
    }

    /**
     * 用自定义的分隔符拼接数据
     *
     * @param collection
     * @param separator
     * @param prefix
     * @param postfix
     * @return
     */
    public static String joinToString(Collection<String> collection, String separator, String prefix, String postfix) {
        StringBuilder sb = new StringBuilder(null == prefix ? "" : prefix);
        String[] strings = collection.toArray(new String[0]);
        for (int i = 0; i < strings.length; i++) {
            if (i > 0) sb.append(separator);
            sb.append(strings[i]);
        }
        sb.append(null == postfix ? "" : postfix);
        return sb.toString();
    }
}
