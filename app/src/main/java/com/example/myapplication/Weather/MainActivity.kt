package com.example.myapplication.Weather


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataStructures.WeatherForDay
import com.example.myapplication.DataStructures.WeatherForMainActivity
import com.example.myapplication.Navigator
import com.example.myapplication.Preferences
import com.example.myapplication.Variables
import com.example.myapplication.Wifi
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if ((Preferences(this).getLat() == "0") and (Preferences(this).getLon() == "0")) {
                     Navigator(this).goToListActivity()
                   }

              binding.b1.setOnClickListener {
                       Preferences(this).deleteData()
                       Navigator(this).goToListActivity()
                   }
               binding.b2.setOnClickListener {
                       showWeather()
               }
               binding.recyclerView.layoutManager = LinearLayoutManager(
                       this,
                       RecyclerView.HORIZONTAL, false
                          )
               binding.imageView.visibility = View.INVISIBLE
               binding.tvtitle.text = Variables().title
               showWeather()
    }

    fun showWeatherToRecyclerView(weatherForWeek: List<WeatherForDay>) {
                binding.recyclerView.adapter = weatherAdapter(weatherList = weatherForWeek)
            }

        fun showWeatherToMainMarkup(weatherForMainActivity: WeatherForMainActivity) {
                binding.date.text = weatherForMainActivity.time
                binding.Name.text = weatherForMainActivity.name
                binding.imageView.visibility = View.VISIBLE
                binding.imageView.setImageResource(
                        WeatherManager().setWeatherImage(
                                weatherForMainActivity.icon
                                    )
                            )
                binding.verd.text = weatherForMainActivity.description
                binding.tempDay.text = weatherForMainActivity.temp
            }

        fun showWeather() {
                if (Wifi().checkInternetConnection(this)) {
                        CoroutineScope(Job()).launch {
                                val weatherForWeek = WeatherManager().getWeatherForWeek(this@MainActivity)
                                val weatherForMainMarkup = WeatherManager().getWeatherForMainMarkup(this@MainActivity)
                                launch(Dispatchers.Main) {
                                        showWeatherToRecyclerView(weatherForWeek)
                                        showWeatherToMainMarkup(weatherForMainMarkup)
                                }
                            }
                    } else {
                        Wifi().noConnection(this)
                    }
            }










}

