package com.titlark.utils;

import com.titlark.entity.AreaCodeMappingTable;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证工具类
 *
 * @author lark
 */
public class IDCardUtil {

    /**
     * 18位身份证正则
     */
    private static final String ID_CARD_REGEX = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$";

    private IDCardUtil() {
    }

    /**
     * 校验身份证号码是否合法
     *
     * @param idCard 身份证号码
     * @return true：合法的身份证号码 false：不合法的身份证号码
     */
    public static boolean isValidIdCard(String idCard) {
        if (null == idCard || idCard.length() != 18) {
            return false;
        }
        Pattern pattern = Pattern.compile(ID_CARD_REGEX);
        Matcher matcher = pattern.matcher(idCard);
        return matcher.matches() && checkChecksum(idCard);
    }

    /**
     * 校验身份证的校验位
     *
     * @param idCard 身份证号码
     * @return true：合法的校验码 false：不合法的校验码
     */
    private static boolean checkChecksum(String idCard) {
        int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] checkCode = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (idCard.charAt(i) - '0') * weight[i];
        }
        return checkCode[sum % 11] == idCard.charAt(17);
    }

    /**
     * 获取出生日期
     *
     * @param idCard 身份证号码
     * @return 出生日期
     */
    public static String getBirthday(String idCard) {
        if (!isValidIdCard(idCard)) {
            throw new RuntimeException("无效的身份证号码");
        }
        return idCard.substring(6, 14); // 身份证号码的出生日期在第7到第14位
    }

    /**
     * 根据身份证号码获取年龄
     *
     * @param idCard 身份证号码
     * @return 年龄，如果身份证号码无效则返回 -1
     */
    public static int getAge(String idCard) {
        // 校验是否是合法的身份证号码
        if (!isValidIdCard(idCard)) {
            throw new RuntimeException("无效的身份证号码");
        }
        try {
            // 提取出生日期
            String birthDateStr = idCard.substring(6, 14); // 取第7到14位
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 计算年龄
            return Period.between(birthDate, currentDate).getYears();
        } catch (Exception e) {
            // 如果日期解析失败，返回无效年龄
            return -1;
        }
    }

    /**
     * 根据身份证号码获取性别
     *
     * @param idCard 身份证号码
     * @return 男或女
     */
    public static String getGender(String idCard) {
        // 校验是否是合法的身份证号码
        if (!isValidIdCard(idCard)) {
            throw new RuntimeException("无效的身份证号码");
        }
        // 获取倒数第二位
        char genderChar = idCard.charAt(16);
        // 判断是否是数字
        if (!Character.isDigit(genderChar)) {
            throw new RuntimeException("无效的身份证号码");
        }
        int genderNumber = Character.getNumericValue(genderChar);

        // 奇数为男性，偶数为女性
        return (genderNumber % 2 == 0) ? "女" : "男";
    }

    /**
     * 获取身份证归属地 (根据身份证前6位)
     *
     * @param idCard 身份证号码
     * @return 身份证号码归属地
     */
    public static String getRegion(String idCard) {
        if (!isValidIdCard(idCard)) {
            throw new RuntimeException("无效的身份证号码");
        }
        String regionCode = idCard.substring(0, 6); // 身份证号码的前6位是地址码
        // 此处可根据 regionCode 查询一个省市县的映射表
        return AreaCodeMappingTable.getRegion(regionCode);
    }

}