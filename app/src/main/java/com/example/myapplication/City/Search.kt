package com.example.myapplication.City

import com.example.myapplication.DataStructures.City
import java.util.*
import kotlin.collections.ArrayList


class Search( var cityList: List<City>) {
    fun filterWithQuery(query: String): List<City> {
        if (query.isNotEmpty()) {
            val filteredList: List<City> = onFilterChanged(query)
            return filteredList
        } else if (query.isEmpty()) {
            return cityList
        }
        return cityList
    }


    private fun onFilterChanged(filterQuery: String): List<City> {
        val filteredList = ArrayList<City>()
        for (currentCity in cityList) {
            if (currentCity.name.lowercase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(currentCity)
            }
        }
        return filteredList
    }
}

