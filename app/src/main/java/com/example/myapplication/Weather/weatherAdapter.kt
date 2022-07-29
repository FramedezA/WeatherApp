package com.example.myapplication.Weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataStructures.WeatherForDay
import com.example.myapplication.R

class weatherAdapter(val weatherList: List<WeatherForDay>) :
        RecyclerView.Adapter<weatherAdapter.weatherHolder>() {

        class weatherHolder(item: View) : RecyclerView.ViewHolder(item) {
                val date: TextView = item.findViewById(R.id.textViewDate)
                val tempDay: TextView = item.findViewById(R.id.textViewTempDay)
                val tempNight: TextView = item.findViewById(R.id.textViewTempNight)
                val description: TextView = item.findViewById(R.id.textViewDescription)
                val humidity: TextView = item.findViewById(R.id.textViewHumidity)
                val windSpeed: TextView = item.findViewById(R.id.textViewWindSpeed)
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): weatherHolder {
                val view =
                        LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
                return weatherHolder(view)
            }

        override fun onBindViewHolder(holder: weatherHolder, position: Int) {

                holder.date.text = weatherList[position].time
                holder.tempDay.text = weatherList[position].tempDay
                holder.tempNight.text = weatherList[position].tempNight
                holder.description.text = weatherList[position].description
                holder.humidity.text = weatherList[position].humidity
                holder.windSpeed.text = weatherList[position].windSpeed
            }

        override fun getItemCount(): Int {
                return weatherList.size
            }
    }