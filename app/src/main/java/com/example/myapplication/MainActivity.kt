package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var pref: SharedPreferences? = null
    val apprefs = "TABLE"
    var city = ""
    var gr: TextView? = null
    var verdict: TextView? = null
    var speed: TextView? = null
    var Humidity: TextView? = null
    var Name: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.b1.setOnClickListener(::onclickintent)
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        city = pref?.getString("counter", "").toString()
        val key = "b6ece36feee51af1bff36fc08f70cb7d"
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
        gr = findViewById(R.id.gradus)
        verdict = findViewById(R.id.verd)
        speed = findViewById(R.id.ckvt)
        Humidity = findViewById(R.id.vl)
        Name = findViewById(R.id.Name)
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
            gr?.text = "Температура: $temp° "
            verdict?.text = desc
            speed?.text = "Скорость ветра: $wind_speed м/с"
            Humidity?.text = "Влажность: $humidity"
            Name?.text = name
        }
    }

    fun onclickintent(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}



