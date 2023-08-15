package com.monzo.androidtest.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.monzo.androidtest.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val context: Context) : Interceptor, ConnectivityManager.NetworkCallback() {
    private var isOnline = false
    private var connectivityManager: ConnectivityManager? = null

    init {
        connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager?.registerDefaultNetworkCallback(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isOnline) {
            return chain.proceed(chain.request())
        } else {
            throw IOException(context.getString(R.string.connectivity_error_message))
        }
    }

    override fun onCapabilitiesChanged(
        network: Network,
        capabilities: NetworkCapabilities
    ) {
        isOnline = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
