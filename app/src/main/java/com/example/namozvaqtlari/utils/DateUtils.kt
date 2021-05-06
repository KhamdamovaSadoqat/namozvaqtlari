package com.example.namozvaqtlari.utils

import android.util.Log

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
    }

    fun timeToTextWithHourAndMinutes(time: String): String{
        Log.d("-------------", "timeToTextWithHourAndMinutes: $time")
        val t = time.split(":")
        Log.d("-------------", "timeToTextWithHourAndMinutes: ${t[0]}")
        Log.d("-------------", "timeToTextWithHourAndMinutes: ${t[1]}")
        Log.d("-------------", "timeToTextWithHourAndMinutes: ${t[2]}")
        return "${t[0]}:${t[1]}"

    }
}