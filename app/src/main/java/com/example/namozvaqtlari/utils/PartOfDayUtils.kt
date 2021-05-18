package com.example.namozvaqtlari.utils

import android.content.Context
import android.location.Location
import android.util.Log
import com.example.namozvaqtlari.constants.DEFAULT_LOCATION
import com.example.namozvaqtlari.helper.TimeHelper
import java.util.*

class PartOfDayUtils {

    private lateinit var timeHelper: TimeHelper
    private var savedPrefs = GetSavedLocation()
    private var location: Location? = null

    fun getPart(context: Context): Int {

        location = savedPrefs.getSavedLocation(context)
        timeHelper = location?.let { TimeHelper(it) }?: TimeHelper(DEFAULT_LOCATION)
        var exactTime = timeHelper.getAlarmTime()
        var allTimes = timeHelper.getAllTimes()
        var dataUtils = DateUtils()
        val date = Calendar.getInstance()

        date.timeInMillis = exactTime

        var fullTime = dataUtils.timeToTextWithHourAndMinutesAndSeconds("${date.get(Calendar.HOUR_OF_DAY)}:${date.get(Calendar.MINUTE)}:00")
        Log.d("-------------", "getIcon: exactTime: $exactTime")
        Log.d("-------------", "getIcon: timeString: $fullTime")
        Log.d("-------------", "getIcon: allTime: $allTimes")

        return when {
            allTimes.fajr == fullTime -> 0
            allTimes.thuhr == fullTime -> 2
            allTimes.assr == fullTime -> 3
            allTimes.maghrib == fullTime -> 4
            allTimes.ishaa == fullTime -> 5
            else -> 0
        }

    }
}