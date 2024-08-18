package com.example.plateful.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * NetworkConnectionInterceptor is an [Interceptor] that checks for network connectivity
 * before proceeding with the HTTP request. If there is no network connection,
 * it throws an [IOException].
 *
 * @property context The [Context] used to retrieve the system's [ConnectivityManager].
 */
class NetworkConnectionInterceptor(
    private val context: Context,
) : Interceptor {
    /**
     * Intercepts the HTTP request and checks for network connectivity. If no network
     * connection is detected, it logs a message and throws an [IOException]. Otherwise,
     * it proceeds with the request.
     *
     * @param chain The [Interceptor.Chain] which provides the request to be intercepted.
     * @return The [Response] after proceeding with the request if there is network connectivity.
     * @throws IOException If there is no network connectivity.
     */
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.run {
            if (!isConnected(context)) {
                Log.i("retrofit", "there is no connection")
                throw IOException()
            } else {
                val builder = chain.request().newBuilder()
                return@run chain.proceed(builder.build())
            }
        }

    /**
     * Checks if the device is connected to a network.
     *
     * @param context The [Context] used to access system services.
     * @return `true` if the device is connected to a network (WiFi, Cellular, or Ethernet),
     * `false` otherwise.
     */
    private fun isConnected(context: Context): Boolean {
        val result: Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        result =
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        return result
    }
}
