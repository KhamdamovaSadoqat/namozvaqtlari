package com.example.namozvaqtlari.utils

import android.util.Log
import java.util.*

class DateUtils {

    fun timeToText(h: Int, m: Int, s: Int): String{
        var hour: String = ""
        var minutes: String = ""
        var seconds: String = ""

        if(h<10) hour = "0$h"
        else if(h == 0) hour = "00"
        else hour = "$h"

        if(m<10) minutes = "0$m"
        else if(m == 0) minutes = "00"
        else minutes = "$m"

        if(s<10) seconds = "0$s"
        else if(s == 0) seconds ="00"
        else seconds = "$s"

        return "- $hour:$minutes:$seconds"
        //test passed
    }

    fun timeToTextWithoutMinus(h: Int, m: Int, s: Int): String{
        var hour: String = ""
        var minutes: String = ""
        var seconds: String = ""

        if(h<10) hour = "0$h"
        else if(h == 0) hour = "00"
        else hour = "$h"

        if(m<10) minutes = "0$m"
        else if(m == 0) minutes = "00"
        else minutes = "$m"

        if(s<10) seconds = "0$s"
        else if(s == 0) seconds ="00"
        else seconds = "$s"

        return "$hour:$minutes:$seconds"
        //test passed
    }

    fun timeToTextWithHourAndMinutes(time: String): String{
//        Log.d("-------------", "timeToTextWithHourAndMinutes: $time")
        val t = time.split(":")
        var hour = ""
        var minutes = ""
//        Log.d("-------------", "timeToTextWithHourAndMinutes: ${t[0]}")
//        Log.d("-------------", "timeToTextWithHourAndMinutes: ${t[1]}")

        if(t[0].toInt()<10)  hour = "${t[0]}"
        else if(t[0].toInt() == 0) hour = "0"
        else hour = t[0]

        if(t[1].toInt()<10) minutes = "${t[1]}"
        else if(t[1].toInt()==0) minutes = "0"
        else minutes = t[1]

        return "$hour:$minutes"
        //test passed

    }
    
    fun longToHoursAndMinutes(time: Long): String{
        val date = Calendar.getInstance()
        date.timeInMillis = time
        var hour = ""
        var minutes = ""

        if(date.get(Calendar.HOUR_OF_DAY)<10) hour = "0${date.get(Calendar.HOUR_OF_DAY)}"
        else if(date.get(Calendar.HOUR_OF_DAY) == 0) hour = "00"
        else hour = "${date.get(Calendar.HOUR_OF_DAY)}"

        if(date.get(Calendar.MINUTE)<10) minutes = "0${date.get(Calendar.MINUTE)}"
        else if(date.get(Calendar.MINUTE) == 0) minutes = "00"
        else minutes = "${date.get(Calendar.MINUTE)}"

        return "$hour:$minutes"
    }

}