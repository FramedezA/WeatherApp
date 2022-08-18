package com.example.myapplication.Weather

import android.content.Context
import com.example.myapplication.DataStructures.CurrentWeather
import com.example.myapplication.DataStructures.DailyWeather
import com.example.myapplication.DataStructures.HourlyWeather
import com.example.myapplication.Preferences
import com.example.myapplication.R
import com.example.myapplication.Variables
import org.json.JSONObject
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class WeatherManager() {
    val dateFormat = SimpleDateFormat("dd.MM")
    val df = DecimalFormat("#")
    val dfWindSpeed = DecimalFormat("#.#")
    val timeFormat = SimpleDateFormat("HH:mm")
    fun getDailyWeather(
        lat: String, lon: String
    ): List<DailyWeather> {
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current," +
                    "minutely,hourly,alerts&appid=${Variables().key}&units=metric&lang=ru"


        df.roundingMode = RoundingMode.CEILING
        val apiResponse = URL(url5days).readText()
        val daily = JSONObject(apiResponse).getJSONArray("daily")
        var dailyWeatherForWeek: List<DailyWeather> = listOf()
        for (i in 0..7) {

            val day = daily.getJSONObject(i)
            val main = day.getString("dt").toLong()
            val temp = day.getJSONObject("temp")
            val tempDay = df.format(temp.getString("day").toFloat()).toString()
            val tempNig = df.format(temp.getString("night").toFloat()).toString()
            val weather = day.getJSONArray("weather").getJSONObject(0)
            val desc = weather.getString("description").toString()
            val icon = weather.getString("icon")
            val time = recycleDate(main)

            val weatherForDay = DailyWeather(
                time,
                "$tempDay°",
                "$tempNig°",
                desc,
                icon
            )
            dailyWeatherForWeek += weatherForDay
        }
        return dailyWeatherForWeek

    }

    fun getHourlyWeather(lat: String, lon: String) :List<HourlyWeather>{
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current," +
                    "minutely,daily,alerts&appid=${Variables().key}&units=metric&lang=ru"
        val apiResponse = URL(url5days).readText()
        val hourly = JSONObject(apiResponse).getJSONArray("hourly")
        var hourlyWeather: List<HourlyWeather> = listOf()
        for (i in 0..23) {
            val hour = hourly.getJSONObject(i)
            val main = hour.getString("dt").toLong()
            val temp = df.format(hour.getString("temp").toFloat()).toString()
            val icon = hour
                .getJSONArray("weather").getJSONObject(0).getString("icon")
            val time = recycleDateToTime(main)
            val oneHour = HourlyWeather(
                icon, "$temp°", time
            )
            hourlyWeather += oneHour
        }
        return hourlyWeather
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

    fun getCurrentWeather(lat: String, lon: String): CurrentWeather {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&appid=${Variables().key}&units=metric&lang=ru&lon=$lon"

        val api = URL(url).readText()
        val apiName = JSONObject(api)
        val name = apiName.getString("name")
        val temp = apiName.getJSONObject("main").getString("temp").toFloat().roundToInt().toString()
        val humidity = apiName.getJSONObject("main").getString("humidity")
        val weather = apiName.getJSONArray("weather").getJSONObject(0)
        val description = weather.getString("description")
        val windSpeed = apiName.getJSONObject("wind").getString("speed").toFloat()
        val icon = weather.getString("icon")
        val time = apiName.getString("dt").toLong()
        val date = recycleDate(time)
        val weatherForMainActivity = CurrentWeather(
            name, "$temp°",
            date,
            description,
            icon,
            "$humidity%",
            "${dfWindSpeed.format(windSpeed)} м/с")
        return weatherForMainActivity
    }

    fun recycleDate(dt: Long): String {
        val dayAndMonth = dateFormat.format(dt*1000).toString().split('.')
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
    fun recycleDateToTime(dt: Long):String{
        val time = timeFormat.format(dt*1000).toString()
        return time
    }
}

