package com.titlark.utils;

import org.junit.Test;

public class SnowFlakeUtilTest {

    @Test
    public void testNextId() {
        for (int i = 0; i < 100; i++) {
            long nextId = SnowFlakeUtil.nextId();
            System.out.println("nextId = " + nextId);
        }

    }
}
