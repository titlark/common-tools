package com.titlark.utils;

import junit.framework.TestCase;

public class EmailUtilTest extends TestCase {

    public void testIsValidEmail() {
        String email = "abc@163.com";
        boolean validEmail = EmailUtil.isValidEmail(email);
        System.out.println("validEmail = " + validEmail);
    }
}
