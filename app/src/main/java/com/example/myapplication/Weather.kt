package com.example.myapplication

import android.widget.ImageView
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
        lat: String, lon: String
    ): Array<WeatherForDay> {
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current," +
                    "minutely,hourly,alerts&appid=${variables().key}&units=metric&lang=ru"

        val df = DecimalFormat("#")
        val dfWindSpeed = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        dfWindSpeed.roundingMode = RoundingMode.CEILING
        val sdf = SimpleDateFormat("dd.MM")
        val apiResponse = URL(url5days).readText()
        val daily = JSONObject(apiResponse).getJSONArray("daily")
        var weatherForWeek: Array<WeatherForDay> = arrayOf()
        for (i in 0..7) {

            val day = daily.getJSONObject(i)
            val main = day.getString("dt").toLong()
            val temp = day.getJSONObject("temp")
            val tempDay = df.format(temp.getString("day").toFloat()).toString()
            val tempNig = df.format(temp.getString("night").toFloat()).toString()
            val weather = day.getJSONArray("weather")
            val desc = weather.getJSONObject(0).getString("description").toString()
            val humidity = day.getString("humidity").toString()
            val windSpeed =
                dfWindSpeed.format(day.getString("wind_speed").toFloat()).toString()
            val date = Date(main * 1000)
            val time = sdf.format(date).toString()

            val weatherForDay = WeatherForDay(
                         time,
                "$tempDay°",
                "$tempNig°",
                         desc,
                 "$humidity%",
                "$windSpeed м/с"
            )
            weatherForWeek += weatherForDay
        }
        return weatherForWeek

    }


    fun setWeatherImage(main: String, imageView: ImageView) {
        val draw = when (main) {
            "01d" -> R.drawable._1d
            "02d" -> R.drawable._2d
            "03d" -> R.drawable._3d
            "04d" -> R.drawable._4d
            "09d" -> R.drawable._9d
            "10d" -> R.drawable._10d
            "11d" -> R.drawable._11d
            "13d" -> R.drawable._13d
            "50" -> R.drawable._50d
            else -> R.drawable._1d
        }
        imageView.setImageResource(draw)
    }
}

class WeatherForDay(
    val time: String,
    val tempDay: String,
    val tempNight: String,
    val description: String,
    val humidity: String,
    val windSpeed: String
)