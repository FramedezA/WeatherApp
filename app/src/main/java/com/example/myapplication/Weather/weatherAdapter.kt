package com.example.myapplication.Weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataStructures.DailyWeather
import com.example.myapplication.R

class weatherAdapter(val dailyWeatherList: List<DailyWeather>) :
        RecyclerView.Adapter<weatherAdapter.weatherHolder>() {

        class weatherHolder(item: View) : RecyclerView.ViewHolder(item) {
                val date: TextView = item.findViewById(R.id.textViewDate)
                val tempDay: TextView = item.findViewById(R.id.textViewTempDay)
                val description: TextView = item.findViewById(R.id.textViewDescription)
                val imageView :ImageView  = item.findViewById(R.id.imageViewMain)
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): weatherHolder {
                val view =
                        LayoutInflater.from(parent.context).inflate(R.layout.daily_item_view, parent, false)
                return weatherHolder(view)
            }

        override fun onBindViewHolder(holder: weatherHolder, position: Int) {
                val tempDay = dailyWeatherList[position].tempDay
                val tempNight = dailyWeatherList[position].tempNight
                holder.date.text = dailyWeatherList[position].time
                holder.tempDay.text = tempDay+"/"+tempNight
                holder.description.text = dailyWeatherList[position].description
                holder.imageView.setImageResource( WeatherManager().setWeatherImage(
                    dailyWeatherList[position].icon
                )
                )
            }

        override fun getItemCount(): Int {
                return dailyWeatherList.size
            }
    }