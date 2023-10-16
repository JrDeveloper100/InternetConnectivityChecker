package com.example.internetconnectivitychecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class InternetConnectivityChecker(private val context: Context) {
    private val connectivityLiveData = MutableLiveData<Boolean>()

    val isConnected: LiveData<Boolean>
        get() = connectivityLiveData

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateConnectionStatus()
        }
    }

    fun startMonitoring() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
        updateConnectionStatus()
    }

    fun stopMonitoring() {
        context.unregisterReceiver(networkReceiver)
    }

    private fun updateConnectionStatus() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        val isConnected = networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

        connectivityLiveData.postValue(isConnected)
    }
}