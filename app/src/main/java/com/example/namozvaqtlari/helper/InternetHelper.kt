package com.example.namozvaqtlari.helper

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

class InternetHelper(val context: Context) {

    val TAG = "InternetHelper"
    lateinit var connectivityManager: ConnectivityManager
    lateinit var snackbar: Snackbar
    var flagOfSnackbar = false

    fun checkInternetConnection(): Boolean {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network: Network = connectivityManager.activeNetwork ?: return false
            val actveNetwork: NetworkCapabilities = connectivityManager.getNetworkCapabilities(
                network
            ) ?: return false
//            Log.d(TAG, "checkInternetConnection: ${actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}")
            return when {
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val networkInfo: NetworkInfo? = (connectivityManager.activeNetworkInfo) as NetworkInfo?
            return networkInfo != null && networkInfo.isConnected
        }
    }
}