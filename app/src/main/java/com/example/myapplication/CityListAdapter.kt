package com.example.myapplication


import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class CityListAdapter( val CityList : Array<Array<String>>) :
    RecyclerView.Adapter<CityListAdapter.CityHolder>() {
    class CityHolder(item: View) : RecyclerView.ViewHolder(item){
        val button: Button = item.findViewById(R.id.Listbutton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.city_list_item_view, parent, false)
     return CityHolder(view)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.button.text = CityList[position][0]
        holder.button.setOnClickListener{
            ListActivity().GoToWeatherActivity()
        }
    }

    override fun getItemCount(): Int {
        return CityList.size
    }

}


