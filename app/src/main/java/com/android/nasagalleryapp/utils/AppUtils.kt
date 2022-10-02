package com.android.nasagalleryapp.utils

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException
import java.io.InputStream

object AppUtils {

     fun inputStreamToString(inputStream: InputStream): String? {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }

    fun isNetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService("connectivity") as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isAvailable && info.isConnected
    }
}