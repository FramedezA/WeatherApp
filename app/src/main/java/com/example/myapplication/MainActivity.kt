package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.b1.setOnClickListener(::GoToListActivity)
        var pref: SharedPreferences? = null
        var pref1 : SharedPreferences? = null
        val apprefs = "TABLE"
        val apprefs1 = "TABLE1"
        var lat: String
        var lon: String
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        pref1 = getSharedPreferences(apprefs1, MODE_PRIVATE)
        lat = pref?.getString("keylat", "0").toString()
        lon = pref1?.getString("keylon","0").toString()
        this.data()
        val key = "b6ece36feee51af1bff36fc08f70cb7d"
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&appid=$key&units=metric&lang=ru&lon=$lon"
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current,minutely,hourly,alerts&appid=$key&units=metric&lang=ru"
        doAsync {
            val sdf = SimpleDateFormat("dd.MM")
            val apiResponse = URL(url5days).readText()
            val daily = JSONObject(apiResponse).getJSONArray("daily")
            val day1 = daily.getJSONObject(0)
            val main1 = day1.getString("dt").toLong()
            val temp  = day1.getJSONObject("temp")
            val tempDay = temp.getString("day")
            val tempNig = temp.getString("night")
            val weather = day1.getJSONArray("weather")
            val desc = weather.getJSONObject(0).getString("description")
            val humidity = day1.getString("humidity")
            val windSpeed = day1.getString("wind_speed")
            binding.tempDay.text = "Днём $tempDay"
            binding.tempNight.text = "Ночью $tempNig"
            binding.verd.text = desc
            binding.vl.text = "Влажность:$humidity"
            binding.ckvt.text = "ветер $windSpeed м/c"

            val date1 = Date(main1 * 1000)
            val time1 = sdf.format(date1)
            binding.tv1.text = time1
            val day2 = daily.getJSONObject(1)

            val main2 = daily.getJSONObject(1).getString("dt").toLong()
            val date2 = Date(main2 * 1000)
            val time2 = sdf.format(date2)
            binding.tv2.text = time2
            val main3 = daily.getJSONObject(2).getString("dt").toLong()
            val date3 = Date(main3 * 1000)
            val time3 = sdf.format(date3)
            binding.tv3.text = time3
            val main4 = daily.getJSONObject(3).getString("dt").toLong()
            val date4 = Date(main4 * 1000)
            val time4 = sdf.format(date4)
            binding.tv4.text = time4
            val main5 = daily.getJSONObject(4).getString("dt").toLong()
            val date5 = Date(main5 * 1000)
            val time5 = sdf.format(date5)
            binding.tv5.text = time5
            binding.date.text = time1

            val apiName = URL(url).readText()
            val name = JSONObject(apiName).getString("name")
            binding.Name.text = name
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun data() {
        val sdf = SimpleDateFormat("dd.MM")
        val data1 = Date()
        val time = sdf.format(data1)
        binding.date.text = time

    }

    fun GoToListActivity(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}




