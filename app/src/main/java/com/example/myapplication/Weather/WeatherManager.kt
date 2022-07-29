package com.example.myapplication.Weather

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.myapplication.DataStructures.WeatherForMainActivity
import com.example.myapplication.DataStructures.WeatherForDay
import com.example.myapplication.Preferences
import com.example.myapplication.R
import com.example.myapplication.Variables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherManager() {
    val dateFormat = SimpleDateFormat("dd.MM")
    fun loadWeatherForWeek(
        lat: String, lon: String
    ): List<WeatherForDay> {
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current," +
                    "minutely,hourly,alerts&appid=${Variables().key}&units=metric&lang=ru"

        val df = DecimalFormat("#")
        val dfWindSpeed = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        dfWindSpeed.roundingMode = RoundingMode.CEILING
        val apiResponse = URL(url5days).readText()
        val daily = JSONObject(apiResponse).getJSONArray("daily")
        var weatherForWeek: List<WeatherForDay> = listOf()
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
            val time = dateFormat.format(date).toString()

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

    fun getWeatherForWeek(context: Context): List<WeatherForDay> {
        val lat = Preferences(context).getLat()
        val lon = Preferences(context).getLon()
        val weatherForWeek = loadWeatherForWeek(lat, lon)
        return weatherForWeek
    }

    fun getWeatherForMainMarkup(context: Context): WeatherForMainActivity {
        val lat = Preferences(context).getLat()
        val lon = Preferences(context).getLon()
        val weatherForMainMarkup = loadWeatherForMainMarkup(lat, lon)
        return weatherForMainMarkup
    }


    fun setWeatherImage(main: String): Int {
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
        return draw
    }

    fun loadWeatherForMainMarkup(lat: String, lon: String): WeatherForMainActivity {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&appid=${Variables().key}&units=metric&lang=ru&lon=$lon"

        val api = URL(url).readText()
        val apiName = JSONObject(api)
        val name = apiName.getString("name")
        val temp = apiName.getJSONObject("main").getString("temp").toFloat().roundToInt().toString()
        val weather = apiName.getJSONArray("weather").getJSONObject(0)
        val description = weather.getString("description")
        val icon = weather.getString("icon")
        val time = apiName.getString("dt").toLong()
        val date = recycleDate(time)
        val weatherForMainActivity = WeatherForMainActivity(name,temp,date,description,icon)
        return weatherForMainActivity
    }

    fun recycleDate(dt: Long): String {
        val dayAndMonth = dateFormat.format(dt).toString().split('.')
        val day = dayAndMonth[0].toInt()
        val monthInInt = dayAndMonth[1].toInt()
        val months = listOf(
            "Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября",
            "Октября", "Ноября", "Декабря"
        )
        val month = months[monthInInt - 1]
        val date = "$day $month"
        return date
    }
}

