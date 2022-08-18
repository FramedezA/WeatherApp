package com.example.myapplication


import android.content.Context
import android.content.Context.MODE_PRIVATE
class Preferences(context: Context) {
    var defaultValue = "0"
    val pref =context.getSharedPreferences("TABLE",MODE_PRIVATE)


    fun saveData(lat: String, lon: String) {
        val editor = pref?.edit()
        editor?.putString("keylat", lat)
        editor?.putString("keylon", lon)
        editor?.apply()
    }

    fun getLat():String{
        val lat = pref?.getString("keylat",defaultValue).toString()
        return lat

    }
    fun getLon():String{
        val lon = pref?.getString("keylon",defaultValue).toString()
        return lon
    }
    fun deleteData(){
        val editor = pref?.edit()
        editor?.clear()
        editor?.apply()
    }

}