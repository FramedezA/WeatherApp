package com.example.myapplication.Weather

import androidx.lifecycle.*
import com.example.myapplication.DataStructures.DailyWeather
import kotlinx.coroutines.*


class MainViewModel(val lat: String, val lon: String) : ViewModel() {


    private val daily: MutableLiveData<List<DailyWeather>> by lazy {

        MutableLiveData<List<DailyWeather>>().also {
           loadDaily(lat, lon)
        }
    }


    fun getDaily(): LiveData<List<DailyWeather>> {
        return daily
    }

    private  fun loadDaily(lat: String, lon: String){
        viewModelScope.launch {
//        viewModelScope.launch(Dispatchers.IO) {
            val weather = withContext(Dispatchers.IO) {
                WeatherManager().getDailyWeather(lat, lon)
            }
            daily.value =weather
//            val weather = WeatherManager().getDailyWeather(lat, lon)
//            launch(Dispatchers.Main){
//                daily.value =weather
//            }
        }
    }


}
 class MainViewModelFactory(private val lat: String, private val lon: String):ViewModelProvider.NewInstanceFactory(){
     override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(lat,lon)as T

 }
