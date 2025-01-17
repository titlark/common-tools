package com.titlark.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
    public static String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);

    private DateUtil() {
    }

    /**
     * 将日期字符串转为Date类型
     *
     * @param dateString   字符串日期
     * @param formatString 日期格式
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString, String formatString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.parse(dateString);
    }

    /**
     * 将日期字符串转为Date类型
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        return sdf.parse(dateString);
    }

    /**
     * 生成当前日期目录，格式：yyyyMMdd
     *
     * @return
     */
    public static String generateCurrentTimePath() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前日期指定年数之后的日期
     *
     * @param year 年数
     * @return
     */
    public static Date nextYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
        return calendar.getTime();
    }

    /**
     * 获取当前日期指定天数之后的日期.
     *
     * @param num 相隔天数
     * @return Date 日期
     */
    public static Date nextDay(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + num);
        return curr.getTime();
    }

    /**
     * 添加天数到指定日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDaysToDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * 获取当前日期指定月数之后的日期.
     *
     * @param num 间隔月数
     * @return Date 日期
     * @since 1.0
     */
    public static Date nextMonth(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + num);
        return curr.getTime();
    }

}
