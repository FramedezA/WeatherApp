package com.example.myapplication
import android.util.Log
import org.json.JSONObject
import java.util.*


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
            Log.d("INFO",City.name)

    }

     return CityList
    }

}
class City( val name:String ,val nameSubject:String, val lat:String,val lon:String)