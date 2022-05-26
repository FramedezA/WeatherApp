package com.example.myapplication

import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Weather() {

    fun getWeather(
        lat: String, lon: String, recyclerView: RecyclerView
    ) {


        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current," +
                    "minutely,hourly,alerts&appid=${variables().key}&units=metric&lang=ru"

        doAsync {
            val df = DecimalFormat("#")
            val dfWindSpeed = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            dfWindSpeed.roundingMode = RoundingMode.CEILING
            val sdf = SimpleDateFormat("dd.MM")
            val apiResponse = URL(url5days).readText()
            val daily = JSONObject(apiResponse).getJSONArray("daily")
            var i = -1
            var w: Array<Array<String>> = arrayOf()
            while (i < 7) {
                i++
                val day1 = daily.getJSONObject(i)
                val main1 = day1.getString("dt").toLong()
                val temp = day1.getJSONObject("temp")
                val tempDay = df.format(temp.getString("day").toFloat()).toString()
                val tempNig = df.format(temp.getString("night").toFloat()).toString()
                val weather = day1.getJSONArray("weather")
                val desc = weather.getJSONObject(0).getString("description").toString()
                val humidity = day1.getString("humidity").toString()
                val windSpeed =
                    dfWindSpeed.format(day1.getString("wind_speed").toFloat()).toString()
                val date1 = Date(main1 * 1000)
                val time1 = sdf.format(date1).toString()
                val weatherList: Array<String> =
                    arrayOf(time1, "$tempDay°", "$tempNig°", desc, "$humidity%", "$windSpeed м/с")
                w += weatherList
            }
            recyclerView.adapter = weatherAdapter(weatherList = w)
        }
    }


}
