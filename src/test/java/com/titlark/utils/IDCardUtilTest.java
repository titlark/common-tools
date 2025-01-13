package com.titlark.utils;

import junit.framework.TestCase;

/**
 * 身份证工具测试类
 */
public class IDCardUtilTest extends TestCase {

    private final String idCard = "411111111111111111";

    /**
     * 校验是否是合法的身份证号码
     */
    public void testIsValidIdCard() {
        boolean validIdCard = IDCardUtil.isValidIdCard(idCard);
        System.out.println("validIdCard = " + validIdCard);
    }

    public void testGetRegion() {
        String region = IDCardUtil.getRegion(idCard);
        System.out.println("region = " + region);
    }

    public void testGetAge() {
        int age = IDCardUtil.getAge(idCard);
        System.out.println("age = " + age);
    }

    public void testGetBirthday() {
        String birthday = IDCardUtil.getBirthday(idCard);
        System.out.println("birthday = " + birthday);
    }

    public void testGetGender() {
        String gender = IDCardUtil.getGender(idCard);
        System.out.println("gender = " + gender);
    }
}
