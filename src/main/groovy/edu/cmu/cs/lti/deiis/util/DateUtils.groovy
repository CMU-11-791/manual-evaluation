package edu.cmu.cs.lti.deiis.util

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Encapsulates a SimpleDateFormat object that should be used to print and parse
 * all dates used in the application.
 */
class DateUtils {

    // DateFormat instances are not thread safe so we stick our SimpleDateFormat
    // object in a ThreadLocal.
    static final ThreadLocal<DateFormat> format = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z")
        }
    }

    static String print(long msec) {
        print(new Date(msec))
    }

    static String print(Date date) {
        format.get().format(date)
    }

    static Date parse(String input) {
        format.get().parse(input)
    }
}
