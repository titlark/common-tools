package com.titlark.mask;

import junit.framework.TestCase;

/**
 * 数据脱敏测试类
 */
public class DataMaskingUtilTest extends TestCase {

    public void testMaskName() {
        String maskName = DataMaskingUtil.maskName("士大夫士大夫");
        System.out.println("maskName = " + maskName);
    }

    public void testMaskIdCard() {
        DataMaskingUtil.setDefaultMaskSymbol("#");
        String maskIdCard = DataMaskingUtil.maskIdCard("110101199001011234");
        System.out.println("maskIdCard = " + maskIdCard);
    }

    public void testMask() {
        String maskData = "abc";
        DataMaskingUtil.setDefaultMaskSymbol("#");
        String mask = DataMaskingUtil.mask(maskData, 0,2);
        System.out.println("mask = " + mask);
    }

}
