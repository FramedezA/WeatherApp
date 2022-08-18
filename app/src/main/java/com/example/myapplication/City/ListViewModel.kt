package com.example.myapplication.City

import androidx.lifecycle.*
import com.example.myapplication.DataStructures.City
import com.example.myapplication.Json
import com.example.myapplication.Weather.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel( val json:String): ViewModel(){
  val citiesMutable :MutableLiveData<List<City>> = MutableLiveData()
    val cities :LiveData<List<City>> = citiesMutable
    fun loadCities(){
        viewModelScope.launch {
            val cityList = withContext(Dispatchers.IO) {
                CityManager().getCityList(json)
            }
            citiesMutable.value = cityList
        }
    }
}

class ListViewModelFactory(private val json: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ListViewModel(json) as T

}