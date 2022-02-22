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
        val apprefs = "TABLE"
        var city: String
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        city = pref?.getString("counter", "").toString()
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
        this.data()
    }

    @SuppressLint("SimpleDateFormat")
    fun data() {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val data1 = Date()
        val time = sdf.format(data1)
        binding.date.text = time

    }

    fun GoToListActivity(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}




