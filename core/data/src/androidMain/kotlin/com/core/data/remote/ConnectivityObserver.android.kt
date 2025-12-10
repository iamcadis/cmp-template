package com.core.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class AndroidConnectivityObserver(private val context: Context) : ConnectivityObserver {
    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}