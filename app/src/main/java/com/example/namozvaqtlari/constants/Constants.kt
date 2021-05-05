package com.example.namozvaqtlari.constants

import android.location.Location

const val LATITUDE = "latitude"
const val LONGITUDE = "longitude"
const val MY_PREFS = "myPrefs"
const val LAST_LOCATION_UPDATE = "lastLocationUpdate"
const val LOCATION_REQ_CODE = 1001
var NOTIFICATION_ENABLED = true

val DEFAULT_LOCATION = Location("").apply {
    this.latitude = 41.2827706
    this.longitude = 69.1392825
}