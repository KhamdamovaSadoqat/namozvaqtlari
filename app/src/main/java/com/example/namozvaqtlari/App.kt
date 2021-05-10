package com.example.namozvaqtlari

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class App : Application() {

    var net = 0
    override fun onCreate() {
        super.onCreate()
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network: Network = (cm.activeNetwork ?: Variables.isNetworkAvailable.postValue(false)) as Network
            val actveNetwork: NetworkCapabilities? = (cm.getNetworkCapabilities(network) ?:  Variables.isNetworkAvailable.postValue(false)) as NetworkCapabilities?
            if(
                actveNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)?.equals(1) == true ||
                actveNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)?.equals(1) == true ||
                actveNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)?.equals(1) == true ||
                actveNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)?.equals(1) == true){
                     net=1
                    Variables.isNetworkAvailable .postValue(true)
                
            } else Variables.isNetworkAvailable .postValue(false)
        } else {
            val networkInfo: NetworkInfo? = (cm.activeNetworkInfo) as NetworkInfo?
            if(networkInfo != null && networkInfo.isConnected){
                net =1
                Variables.isNetworkAvailable .postValue(true)
            }
            else Variables.isNetworkAvailable .postValue(false)

            Log.d("-------------", "onCreate: $net")
    }

        


//        cm.registerNetworkCallback(NetworkRequest.Builder().build(),object : ConnectivityManager.NetworkCallback(){
//
//            override fun onAvailable(network: Network) {
//                super.onAvailable(network)
//                Variables.isNetworkAvailable .postValue(true)
//            }
//
//            override fun onLosing(network: Network, maxMsToLive: Int) {
//                super.onLosing(network, maxMsToLive)
//                Variables.isNetworkAvailable.postValue(false)
//            }
//
//            override fun onLost(network: Network) {
//                super.onLost(network)
//                Variables.isNetworkAvailable.postValue(false)
//            }
//
//            override fun onUnavailable() {
//                super.onUnavailable()
//                Variables.isNetworkAvailable.postValue(false)
//            }
//
//        })

    }

}