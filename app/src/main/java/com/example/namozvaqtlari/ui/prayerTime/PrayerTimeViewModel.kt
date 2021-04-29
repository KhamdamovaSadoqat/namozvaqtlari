package com.example.namozvaqtlari.ui.prayerTime

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
//import com.example.prayertime.database.TimesByYearDao
import com.example.namozvaqtlari.helper.TimeHelper
import com.example.namozvaqtlari.model.Times
//import com.example.prayertime.repository.TimesRepository

class PrayerTimeViewModel() : ViewModel() {

//    private val timesRepository = TimesRepository(dataSource)

    fun getDate(location:Location): Times {
        return TimeHelper(location).getAllTimes()
    }

}