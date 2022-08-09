package com.example.myapplication.Weather


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataStructures.DailyWeather
import com.example.myapplication.DataStructures.CurrentWeather
import com.example.myapplication.Navigator
import com.example.myapplication.Preferences
import com.example.myapplication.Wifi
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lat = Preferences(this).getLat()
        val lon = Preferences(this).getLon()
        if ((lat == "0") and (lon == "0")) {
            Navigator(this).goToListActivity()
        }

        binding.b1.setOnClickListener {
            Preferences(this).deleteData()
            Navigator(this).goToListActivity()
        }
        binding.b2.setOnClickListener {
            showWeather()
        }
        binding.hourlyWeather.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )

        val model: MainViewModel by viewModels { MainViewModelFactory(lat, lon) }
        model.getDaily().observe(this) { daily ->
            showWeatherToRecyclerView(daily)
        }

        binding.imageViewMain.visibility = View.INVISIBLE

        showWeather()
    }

    fun showWeatherToRecyclerView(dailyWeatherForWeek: List<DailyWeather>) {
        binding.recyclerView.adapter = weatherAdapter(dailyWeatherList = dailyWeatherForWeek)
    }

    fun showWeatherToMainMarkup(currentWeather: CurrentWeather) {
        binding.date.text = currentWeather.time
        binding.Name.text = currentWeather.name
        binding.imageViewMain.visibility = View.VISIBLE
        binding.imageViewMain.setImageResource(
            WeatherManager().setWeatherImage(
                currentWeather.icon
            )
        )
        binding.verd.text = currentWeather.description
        binding.tempDay.text = currentWeather.temp
    }

    fun showWeather() {
        if (Wifi().checkInternetConnection(this)) {
            CoroutineScope(Job()).launch {

                val weatherForMainMarkup =
                    WeatherManager().getWeatherForMainMarkup(this@MainActivity)
                launch(Dispatchers.Main) {

                    showWeatherToMainMarkup(weatherForMainMarkup)

                }
            }
        } else {
            Wifi().noConnection(this)
        }
    }


}

