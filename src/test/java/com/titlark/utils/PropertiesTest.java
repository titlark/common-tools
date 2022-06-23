package com.titlark.utils;

import org.junit.Test;

public class PropertiesTest {

    @Test
    public void testReadStringProp() {
        String driver = PropertiesUtil.readString("jdbc", "jdbc.driver");
        System.out.println(driver);
    }
}
