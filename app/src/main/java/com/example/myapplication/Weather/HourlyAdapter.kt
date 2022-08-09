package com.example.myapplication.Weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataStructures.HourlyWeather
import com.example.myapplication.R

class HourlyAdapter(val HourlyWeatherList:List<HourlyWeather>)
    :RecyclerView.Adapter<HourlyAdapter.HourlyHolder>() {
    class HourlyHolder(item : View):RecyclerView.ViewHolder(item){
        val time :TextView = item.findViewById(R.id.time)
        val temp :TextView = item.findViewById(R.id.temp)
        val icon :ImageView = item.findViewById(R.id.imageViewHourly)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hourly_item_view, parent, false)
        return HourlyHolder(view)
    }

    override fun onBindViewHolder(holder:HourlyHolder, position: Int) {
        holder.time.text = HourlyWeatherList[position].time
        holder.temp.text = HourlyWeatherList[position].temp
        holder.icon.setImageResource( WeatherManager().setWeatherImage(
            HourlyWeatherList[position].icon
        )
        )
    }

    override fun getItemCount(): Int {
        return HourlyWeatherList.size
    }
}