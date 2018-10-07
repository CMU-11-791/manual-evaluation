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

    @Ignore
    void randomTest() {
        Random r = new Random()
        10.times {
            println r.nextInt(2)
        }
    }
    @Ignore
    void shuffleTest() {
        List list = [1,2,3,4]
        10.times {
            println shuffle(list)
        }
    }

    List shuffle(List list) {
        Random random = new Random()
        int n = list.size() - 1;
        while (n > 1) {
            int i = random.nextInt(n - 1)
            list.swap(i, n)
            --n
        }
        return list
    }
}
