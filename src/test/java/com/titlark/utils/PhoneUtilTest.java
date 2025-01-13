package com.titlark.utils;

import junit.framework.TestCase;

/**
 * 电话工具类
 */
public class PhoneUtilTest extends TestCase {

    private final String phoneNumber = "15666666666";

    public void testIsValidPhoneNumber() {
        boolean validPhoneNumber = PhoneUtil.isValidPhoneNumber(phoneNumber);
        System.out.println("validPhoneNumber = " + validPhoneNumber);
    }
}
