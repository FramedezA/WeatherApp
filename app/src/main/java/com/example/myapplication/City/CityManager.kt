package com.example.myapplication.City
import android.util.Log
import com.example.myapplication.DataStructures.City
import org.json.JSONObject



class CityManager {
    fun getCityList(JsonFile:String):List<City>{
        var CityList: List<City> =listOf()
        for(i in 0..1116){
            val b = JSONObject(JsonFile).getJSONArray("list")
            val cityObject = b.getJSONObject(i)
            val cityName = cityObject.getString("name")
            val citySubject = cityObject.getString("subject")
            val coords = cityObject.getJSONObject("coords")
            val latL  = coords.getString("lat")
            val lonL  = coords.getString("lon")
            val City =City(cityName,citySubject,latL,lonL)
            CityList += City

    }

     return CityList
    }

}
