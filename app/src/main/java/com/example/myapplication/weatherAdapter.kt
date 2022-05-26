package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class weatherAdapter(val weatherList: Array<Array<String>>):
    RecyclerView.Adapter<weatherAdapter.weatherHolder>() {

    class weatherHolder(item : View):RecyclerView.ViewHolder(item){
        val date :TextView = item.findViewById(R.id.textViewDate)
        val tempD :TextView = item.findViewById(R.id.textViewTempDay)
        val tempN:TextView = item.findViewById(R.id.textViewTempNight)
        val desc:TextView = item.findViewById(R.id.textViewDescription)
        val humidity:TextView = item.findViewById(R.id.textViewHumidity)
        val windSpeed:TextView = item.findViewById(R.id.textViewWindSpeed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): weatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
          return weatherHolder(view)
    }

    override fun onBindViewHolder(holder: weatherHolder, position: Int) {

        holder.date.text = weatherList[position][0]
        holder.tempD.text = weatherList[position][1]
        holder.tempN.text = weatherList[position][2]
        holder.desc.text = weatherList[position][3]
        holder.humidity.text = weatherList[position][4]
        holder.windSpeed.text = weatherList[position][5]
    }

    override fun getItemCount(): Int {
         return  weatherList.size
    }
}
