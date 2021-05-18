package com.example.namozvaqtlari.helper

import android.location.Location
import android.util.Log
import com.azan.Azan
import com.azan.Madhhab
import com.azan.Method
import com.azan.Time
import com.azan.astrologicalCalc.SimpleDate
import com.example.namozvaqtlari.model.Times
import java.util.*

class TimeHelper(private val location: Location) {

    private var times = arrayOf<Time>()
    private val mCalendar = Calendar.getInstance()
    private lateinit var simpleDate: SimpleDate

    init {
        mCalendar.time = Date()
        prayerTime()
        checkIsTimeAvailable()
    }


    private fun prayerTime() {

        getSimpleDate()

        val method = Method.NORTH_AMERICA
        method.madhhab = Madhhab.HANAFI
        val gmt = TimeZone.getDefault().rawOffset / 3600000.toDouble()
        val azanLocation =
            com.azan.astrologicalCalc.Location(location.latitude, location.longitude, gmt, 0)
        val azan = Azan(azanLocation, method)

        val prayerTimes = azan.getPrayerTimes(simpleDate)
        times = prayerTimes.times
//        Log.d("Times", "prayerTime: ${times[0]}, ${times[1]}, ${times[2]}, ${times[3]}, ${times[4]}, ${times[5]}, ")
    }

    private fun checkIsTimeAvailable() {
        val year = mCalendar.get(Calendar.YEAR)
        val month = mCalendar.get(Calendar.MONTH)
        val day = mCalendar.get(Calendar.DATE)
        val ishaa = mCalendar
        ishaa.set(year, month, day, times[5].hour, times[5].minute)
        if (Date().time > ishaa.time.time) {
            mCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    private fun getSimpleDate() {
        val day = mCalendar.get(Calendar.DAY_OF_MONTH)
        val month = mCalendar.get(Calendar.MONTH) + 1
        val year = mCalendar.get(Calendar.YEAR)
//        Log.d("------------", "getSimpleDate: $day $month $year")
        simpleDate = SimpleDate(day, month, year)
    }

    fun getAlarmTime(): Long {
        val year = mCalendar.get(Calendar.YEAR)
        val month = mCalendar.get(Calendar.MONTH)
        val day = mCalendar.get(Calendar.DATE)
        val cal = mCalendar
        val now = Date().time
        (times.indices).forEach loop@{
            if (it == 1) return@loop
            cal.set(year, month, day, times[it].hour, times[it].minute, times[it].second)
//            Log.d(
//                "------------",
//                "getAlarmTime: ${cal.get(Calendar.DAY_OF_MONTH)}  ${cal.get(Calendar.HOUR_OF_DAY)} ${
//                    cal.get(Calendar.MINUTE)
//                }"
//            )
            Log.d("-------------", "getAlarmTime: now: $now")
            Log.d("-------------", "getAlarmTime: cal: ${cal.timeInMillis}")
            if (cal.timeInMillis > now) return cal.timeInMillis
        }
        cal.set(year, month, day+1, times[0].hour, times[0].minute)
        Log.d("-------------", "getAlarmTime: ${cal.timeInMillis}")

        return cal.timeInMillis
    }

    fun getAlarmTimeOneBefore(): Int {
        val year = mCalendar.get(Calendar.YEAR)
        val month = mCalendar.get(Calendar.MONTH)
        val day = mCalendar.get(Calendar.DATE)
        val cal = mCalendar
        val now = Date().time
        var flag = 6
        (times.indices).forEach loop@{
            flag = it
            if (it == 1) return@loop
            if(flag == 0) flag = 6
            if(flag == 2) flag = 1
            Log.d("-------------", "getAlarmTimeOneBefore: flag: $flag")
            Log.d("-------------", "getAlarmTimeOneBefore: it: $it")
            cal.set(year, month, day, times[it].hour, times[it].minute, times[it].second)
//            Log.d(
//                "------------",
//                "getAlarmTime: ${cal.get(Calendar.DAY_OF_MONTH)}  ${cal.get(Calendar.HOUR_OF_DAY)} ${
//                    cal.get(Calendar.MINUTE)
//                }"
//            )
            if (cal.timeInMillis > now) return flag-1
        }
        cal.set(year, month, day+1, times[0].hour, times[0].minute)
        Log.d("-------------", "getAlarmTime: ${cal.timeInMillis}")

        return flag
    }

    fun getAllTimes(): Times {
        return Times(
            simpleDate,
            times[0].toString(),
            times[1].toString(),
            times[2].toString(),
            times[3].toString(),
            times[4].toString(),
            times[5].toString()
        )
    }
}
