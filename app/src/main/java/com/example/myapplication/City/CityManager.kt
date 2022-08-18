package com.example.myapplication.City

import android.util.Log
import com.example.myapplication.DataStructures.City
import org.json.JSONObject


class CityManager {
    fun getCityList(json: String): MutableList<City> {
        val cityList: MutableList<City> = mutableListOf()
        val jsonCityArray = JSONObject(json).getJSONArray("list")
        for (i in 0 until jsonCityArray.length()) {
            val cityObject = jsonCityArray.getJSONObject(i)
            val cityName = cityObject.getString("name")
            val citySubject = cityObject.getString("subject")
            val coords = cityObject.getJSONObject("coords")
            val latL = coords.getString("lat")
            val lonL = coords.getString("lon")
            val city = City(cityName, citySubject, latL, lonL)
            cityList.add(city)
        }
        return cityList
    }

}