package com.example.namozvaqtlari.utils

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.util.Log
import com.example.namozvaqtlari.constants.LATITUDE
import com.example.namozvaqtlari.constants.LONGITUDE
import com.example.namozvaqtlari.constants.MY_PREFS

class GetSavedLocation {

    private lateinit var prefs: SharedPreferences

    fun getSavedLocation(context: Context): Location? {
        prefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        val latitudeSt = prefs.getString(LATITUDE, null)
        val longtitudeSt = prefs.getString(LONGITUDE, null)
        var location: Location? = null
        if (latitudeSt != null && longtitudeSt != null) {
            location = Location("")
            location.latitude = latitudeSt.toDouble()
            location.longitude = longtitudeSt.toDouble()
        }
        return location
    }
}