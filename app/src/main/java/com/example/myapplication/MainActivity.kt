package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Wifi().isOnline(baseContext)
        binding.b1.setOnClickListener { GoToListActivity() }
        binding.b2.setOnClickListener(::reloadingWeatherData)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )
        binding.tvtitle.text = variables().title
        binding.date.text = getDate()
        getFullWeather()
        binding.imageView.visibility = View.INVISIBLE
    }


    override fun onStart() {
        super.onStart()
        pref = getSharedPreferences("TABLE", MODE_PRIVATE)
        val lat = Preferences().getLat(pref)
        val lon = Preferences().getLon(pref)
        if ((lat == "0") and (lon == "0")) {
            GoToListActivity()
        }
    }


    fun getFullWeather() {
        pref = getSharedPreferences("TABLE", MODE_PRIVATE)
        val coordList: List<String> = Preferences().GetData(pref)
        val lat = coordList[0]
        val lon = coordList[1]
        if(Wifi().isOnline(this)){
        CoroutineScope(Job()).launch {

            val weatherForWeek = Weather().getWeather(lat, lon)

            launch { getCityName(lat, lon) }
            runOnUiThread {
                binding.imageView.visibility= View.VISIBLE
                binding.recyclerView.adapter = weatherAdapter(weatherList = weatherForWeek)
            }
        }}
        else{
            val toast = Toast.makeText(this,"Нет подключения к интернету", Toast.LENGTH_SHORT)
            toast.show()
        }

    }



    fun GoToListActivity() {
        pref = getSharedPreferences("TABLE", MODE_PRIVATE)
        Preferences().deleteData(pref)
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)


    }

    fun reloadingWeatherData(view: View) {
        getFullWeather()

    }


    @SuppressLint("SetTextI18n")
    fun getDate(): String {
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
        val api = URL(url).readText()
        val apiName = JSONObject(api)
        val name = apiName.getString("name")
        val Temp = apiName.getJSONObject("main").getString("temp").toFloat()
        val weather = apiName.getJSONArray("weather").getJSONObject(0)
        val description = weather.getString("description")
        val icon = weather.getString("icon")
        Weather().setWeatherImage(icon,binding.imageView)
        binding.verd.text = description
        binding.Name.text = name
        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.CEILING
        binding.tempDay.text = df.format(Temp) + "°"
    }
}

