package com.example.myapplication.Weather


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataStructures.DailyWeather
import com.example.myapplication.DataStructures.CurrentWeather
import com.example.myapplication.DataStructures.HourlyWeather
import com.example.myapplication.Navigator
import com.example.myapplication.Preferences
import com.example.myapplication.Wifi
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var model :MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lat = Preferences(this).getLat()
        val lon = Preferences(this).getLon()
        if ((lat == "0") and (lon == "0")) {
            Navigator(this).goToListActivity()
        }
        model = ViewModelProvider(this,MainViewModelFactory(lat, lon))[MainViewModel::class.java]
        model.dailyWeather.observe(this) { daily ->
            showWeatherToRecyclerView(daily)
        }
        model.hourlyWeather.observe(this) { hourly ->
            showHourlyWeather(hourly)
        }
        model.currentWeather.observe(this) { current ->
            showWeatherToMainMarkup(current)
        }
        if (Wifi().checkInternetConnection(this)) {
            model.loadWeather()
        } else {
            Snackbar.make(binding.root, "Нет подключения к интернету", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }
        binding.goToCities.setOnClickListener {
            Preferences(this).deleteData()
            Navigator(this).goToListActivity()
        }
        binding.refreshWeather.setOnClickListener { view ->
            if (Wifi().checkInternetConnection(this)) {
                model.loadWeather()
            } else {
                Snackbar.make(view, "Нет подключения к интернету", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
        binding.hourlyWeather.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )


    }


    fun showWeatherToRecyclerView(dailyWeatherForWeek: List<DailyWeather>) {
        binding.recyclerView.adapter = WeatherAdapter(dailyWeatherList = dailyWeatherForWeek)
    }

    fun showHourlyWeather(hourlyWeather: List<HourlyWeather>) {
        binding.hourlyWeather.adapter = HourlyAdapter(hourlyWeather)
    }

    fun showWeatherToMainMarkup(currentWeather: CurrentWeather) {
        binding.date.text = currentWeather.time
        binding.Name.text = currentWeather.name
        binding.imageViewMain.setImageResource(
            WeatherManager().setWeatherImage(
                currentWeather.icon
            )
        )
        binding.verd.text = currentWeather.description
        binding.tempDay.text = currentWeather.temp
        binding.humidityResult.text = currentWeather.humidity
        binding.windSpeedResult.text = currentWeather.windSpeed
    }


}

