package com.titlark.utils;

import junit.framework.TestCase;

import java.text.ParseException;
import java.util.Date;

public class DateUtilTest extends TestCase {

    public void testStringToDate() throws ParseException {
        String dateString ="2025-01-14";
        String formatString = "yyyy-MM-dd";
        Date date = DateUtil.parseDate(dateString, formatString);
        System.out.println("date = " + date);
    }
}
