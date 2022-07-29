package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast


class Wifi {


        fun checkInternetConnection(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetwork
            if (null != networkInfo) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {

                    return true
                }
            } else {
                return false
            }
            return false
        }



    fun noConnection(context: Context) {
        val toast =
            Toast.makeText(
                context, "Нет подключения к интернету".trimMargin(),
                Toast.LENGTH_SHORT
            )
        toast.show()
    }

}