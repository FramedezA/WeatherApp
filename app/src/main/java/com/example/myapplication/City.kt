package com.example.myapplication
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject



class bb  {
    fun jsoner(JsonFile:String,recyclerView: RecyclerView){
        var i = -1
        val b = JSONObject(JsonFile).getJSONArray("list")
        var CityList: Array<Array<String>> = arrayOf()
        while(i<1116){
            i++
        val cityObject = b.getJSONObject(i)
        val cityName = cityObject.getString("name")
        val citySubject = cityObject.getString("subject")
        val coords = cityObject.getJSONObject("coords")
        val latL  = coords.getString("lat")
        val lonL  = coords.getString("lon")
         val CityInfo :Array<String> = arrayOf(cityName,citySubject,latL,lonL)
            CityList += CityInfo
    }
        recyclerView.adapter =CityListAdapter(CityList = CityList)
    }


}