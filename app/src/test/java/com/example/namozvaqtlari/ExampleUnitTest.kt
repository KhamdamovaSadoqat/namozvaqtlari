package com.example.namozvaqtlari

import com.example.namozvaqtlari.utils.DateUtils
import com.google.android.gms.common.util.DataUtils
import org.junit.Test

import org.junit.Assert.*
import java.io.OutputStream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun dataUtils(){

        val time = DateUtils()
        var st = ""

        for (h in 0..23){
            for(m in 0..59){
//                for (s in 0..59){
                     if(h<10){
                         st = "0$h"
                     } else st = "$h"
                    if(m<10){
                        st = "$st:0$m"
                    }else st = "$st:$m"
//                    if(s<10){
//                        st = "$st:0$s"
//                    }else st = "$st:$s"
                    assertEquals(st,time.timeToTextWithHourAndMinutes(st))
                    println(time.timeToTextWithHourAndMinutes(st))
                }
//            }
        }
    }

}