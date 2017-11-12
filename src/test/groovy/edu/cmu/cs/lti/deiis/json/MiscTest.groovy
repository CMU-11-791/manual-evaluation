package edu.cmu.cs.lti.deiis.json

import edu.cmu.cs.lti.deiis.controllers.Main
import org.junit.Ignore
import org.junit.Test

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 *
 */
class MiscTest {

    @Ignore
    void test() {
        long now = System.currentTimeMillis()
        SimpleDateFormat est = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z")
        println new Date().toGMTString()
        println est.format(new Date(now))
        est.setTimeZone(TimeZone.getTimeZone('GMT'))
        println est.format(new Date(now))

        Date d = new Date(now)
        println d.getTime()
        println now
    }
}
