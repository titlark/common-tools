package com.titlark.utils;

import junit.framework.TestCase;

public class SnowFlakeUtilTest extends TestCase {

    public void testNextId() {
        for (int i = 0; i < 100; i++) {
            long nextId = SnowFlakeUtil.nextId();
            System.out.println("nextId = " + nextId);
        }

    }
}
