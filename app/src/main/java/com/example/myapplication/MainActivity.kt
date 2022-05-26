package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isOnline = Wifi().isOnline(baseContext)
        binding.b1.setOnClickListener(::GoToListActivity)
        binding.b2.setOnClickListener(::reloadingWeatherData)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )
        val pref: SharedPreferences?
        val pref1: SharedPreferences?
        val apprefs = "TABLE"
        val apprefs1 = "TABLE1"
        pref = getSharedPreferences(apprefs, AppCompatActivity.MODE_PRIVATE)
        pref1 = getSharedPreferences(apprefs1, AppCompatActivity.MODE_PRIVATE)
        val lat = pref?.getString("keylat", "0").toString()
        val lon = pref1?.getString("keylon", "0").toString()
        getCityName(lat, lon)
        binding.tvtitle.text = variables().title
        binding.date.text = getDate()
        Weather().getWeather( lat, lon, binding.recyclerView)


    }


    fun GoToListActivity(view: View) {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)

    }

    fun reloadingWeatherData(view: View) {
        Wifi().isOnline(baseContext)
        getCityName(variables().lat, variables().lon)

    }


    @SuppressLint("SetTextI18n")
    fun getDate():String {
        val sdfDay = SimpleDateFormat("dd")
        val dateDay = sdfDay.format(Date()).toInt()
        val sdfMonth = SimpleDateFormat("MM")
        var dateMonth = sdfMonth.format(Date()).toInt()
        var month = when (dateMonth) {
            1 -> "Января"
            2 -> "Февраля"
            3 -> "Марта"
            4 -> "Апреля"
            5 -> "Мая"
            6 -> "Июня"
            7 -> "Июля"
            8 -> "Августа"
            9 -> "Сентября"
            10 -> "Октября"
            11 -> "Ноября"
            12 -> "Декабря"
            else -> ""
        }
        val date = "$dateDay $month"
        return date
    }

    fun getCityName(lat: String, lon: String) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&appid=${variables().key}&units=metric&lang=ru&lon=$lon"
        doAsync {
            val api = URL(url).readText()
            val apiName = JSONObject(api)
            val name = apiName.getString("name")
            val Temp = apiName.getJSONObject("main").getString("temp").toFloat()
            val weather1 = apiName.getJSONArray("weather").getJSONObject(0)
            val description = weather1.getString("description")
            binding.verd.text = description
            binding.Name.text = name
            val df = DecimalFormat("#")
            df.roundingMode = RoundingMode.CEILING
            binding.tempDay.text = df.format(Temp) + "°"

        }
    }


}

