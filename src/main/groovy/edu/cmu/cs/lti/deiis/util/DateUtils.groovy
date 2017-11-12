package edu.cmu.cs.lti.deiis.util

import java.text.SimpleDateFormat

/**
 * Encapsulates a SimpleDateFormat object that should be used to print and parse
 * all dates used in the application.
 */
class DateUtils {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z")

    static String print(long msec) {
        print(new Date(msec))
    }

    static String print(Date date) {
        format.format(date)
    }

    static Date parse(String input) {
        format.parse(input)
    }
}
