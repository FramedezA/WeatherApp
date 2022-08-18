package com.example.myapplication.Weather

import androidx.lifecycle.*
import com.example.myapplication.DataStructures.CurrentWeather
import com.example.myapplication.DataStructures.DailyWeather
import com.example.myapplication.DataStructures.HourlyWeather
import kotlinx.coroutines.*


class MainViewModel(val lat: String, val lon: String) : ViewModel() {
    val weatherManager = WeatherManager()

    private val dailyMutable: MutableLiveData<List<DailyWeather>> = MutableLiveData()
    private val hourlyMutable: MutableLiveData<List<HourlyWeather>> = MutableLiveData()
    private val currentMutable: MutableLiveData<CurrentWeather> = MutableLiveData()

    val currentWeather: LiveData<CurrentWeather> = currentMutable
    val hourlyWeather: LiveData<List<HourlyWeather>> = hourlyMutable
    val dailyWeather: LiveData<List<DailyWeather>> = dailyMutable

   fun loadWeather(){
        viewModelScope.launch {
            val currentWeather = withContext(Dispatchers.IO) {
                weatherManager.getCurrentWeather(lat, lon)
            }
            val hourlyWeather = withContext(Dispatchers.IO) {
                weatherManager.getHourlyWeather(lat, lon)
            }
            val dailyWeather = withContext(Dispatchers.IO) {
                weatherManager.getDailyWeather(lat, lon)
            }
            dailyMutable.value = dailyWeather
            hourlyMutable.value = hourlyWeather
            currentMutable.value = currentWeather
        }
    }




}

class MainViewModelFactory(private val lat: String, private val lon: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(lat, lon) as T

}
