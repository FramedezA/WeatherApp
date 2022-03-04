package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
        val apprefs = "TABLE"
        var city: String
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        city = pref?.getString("counter", "").toString()
        this.data()
        val key = "b6ece36feee51af1bff36fc08f70cb7d"
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
        doAsync {
            val apiResponse = URL(url).readText()
            val weather = JSONObject(apiResponse).getJSONArray("weather")
            val desc = weather.getJSONObject(0).getString("description")
            val main = JSONObject(apiResponse).getJSONObject("main")
            val temp = main.getString("temp")
            val wind = JSONObject(apiResponse).getJSONObject("wind")
            val wind_speed = wind.getString("speed")
            val humidity = main.getString("humidity")
            val name = JSONObject(apiResponse).getString("name")
            binding.gradus.text = "$temp°"
            binding.verd.text = desc
            binding.ckvt.text = " $wind_speed м/с ветер"
            binding.vl.text = "Влажность: $humidity %"
            binding.Name.text = name
        }
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=55.75&lon=37.61&exclude=current,minutely,hourly,alerts&appid=b6ece36feee51af1bff36fc08f70cb7d&units=metric&lang=ru"
        doAsync {
            val apiResponse = URL(url5days).readText()
            val list = JSONObject(apiResponse).getJSONArray("daily")
            val main1 = list.getJSONObject(0).getString("dt").toLong()
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val date1 = Date(main1 * 1000)
            val time1 = sdf.format(date1)
            binding.tv1.text = time1
            val main2 = list.getJSONObject(1).getString("dt").toLong()
            val date2 = Date(main2 * 1000)
            val time2 = sdf.format(date2)
            binding.tv2.text = time2
            val main3 = list.getJSONObject(2).getString("dt").toLong()
            val date3 = Date(main3 * 1000)
            val time3 = sdf.format(date3)
            binding.tv3.text = time3
            val main4 = list.getJSONObject(3).getString("dt").toLong()
            val date4 = Date(main4 * 1000)
            val time4 = sdf.format(date4)
            binding.tv4.text = time4
            val main5 = list.getJSONObject(4).getString("dt").toLong()
            val date5 = Date(main5 * 1000)
            val time5 = sdf.format(date5)
            binding.tv5.text = time5
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




