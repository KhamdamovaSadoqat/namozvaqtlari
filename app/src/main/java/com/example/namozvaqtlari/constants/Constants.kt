package com.example.namozvaqtlari.constants

import android.location.Location

const val LATITUDE = "latitude"
const val LONGITUDE = "longitude"
const val MY_PREFS = "myPrefs"
const val LAST_LOCATION_UPDATE = "lastLocationUpdate"
const val LOCATION_REQ_CODE = 1001
const val FACEBOOK_URL = "https://www.facebook.com/idrok.talim"
const val FACEBOOK_PAGE_ID = "YourPageName";
var LOCATION_PERMISTON = false
var NOTIFICATION_ENABLED = true
var TIME: Long = 0


val DEFAULT_LOCATION = Location("").apply {
    this.latitude = 41.3111619
    this.longitude = 69.2704251
}