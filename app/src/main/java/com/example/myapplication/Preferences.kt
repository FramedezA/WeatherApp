package com.example.myapplication

import android.app.Activity
import android.app.ActivityManager
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.util.Log

class Preferences() {
    var defaultValue = "0"


    fun saveData(lat: String, lon: String,pref :SharedPreferences?) {
        val editor = pref?.edit()
        editor?.putString("keylat", lat)
        editor?.putString("keylon", lon)
        editor?.apply()
        Log.d("INFO","$lat,$lon")
    }
    fun GetData(pref :SharedPreferences?):List<String>{

        val lat = pref?.getString("keylat", defaultValue).toString()
        val lon = pref?.getString("keylon", defaultValue).toString()
        val coordList = listOf(lat,lon)
        return coordList
    }
    fun getLat(pref :SharedPreferences?):String{
        val lat = pref?.getString("keylat",defaultValue).toString()
        return lat

    }
    fun getLon(pref :SharedPreferences?):String{
        val lon = pref?.getString("keylon",defaultValue).toString()
        return lon
    }
    fun deleteData(pref :SharedPreferences?){
        val editor = pref?.edit()
        editor?.clear()
        editor?.apply()
        Log.d("INFO","делитнуто")
    }

}