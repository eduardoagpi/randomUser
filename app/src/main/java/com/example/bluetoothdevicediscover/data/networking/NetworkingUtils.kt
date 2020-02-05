package com.example.bluetoothdevicediscover.data.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkingUtils {

    fun isNetworkConnected(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.run {
            return if (Build.VERSION.SDK_INT < 23) {
                activeNetworkInfo?.run {
                    isConnected && (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE)
                } ?: false
            } else {
                activeNetwork?.run {
                    getNetworkCapabilities(this)?.run {
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(
                            NetworkCapabilities.TRANSPORT_WIFI
                        )
                    } ?: false
                } ?: false
            }
        } ?: false
}
