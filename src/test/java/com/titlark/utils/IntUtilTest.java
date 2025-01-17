package com.titlark.utils;

import junit.framework.TestCase;

public class IntUtilTest extends TestCase {

    public void testGetGreatestCommonDivisor() {
        int greatestCommonDivisor = IntUtil.getGreatestCommonDivisor(6, 8);
        System.out.println("greatestCommonDivisor = " + greatestCommonDivisor);
    }
}
