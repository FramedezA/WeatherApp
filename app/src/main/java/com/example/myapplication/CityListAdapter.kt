package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CityListAdapter( val CityList : List<City>,
                       private val listener : OnItemClickListener
                       ) :
    RecyclerView.Adapter<CityListAdapter.CityHolder>() {


      inner class CityHolder(item: View) : RecyclerView.ViewHolder(item),View.OnClickListener{
        val CardView: CardView = item.findViewById(R.id.CardView)
          val TextViewName :TextView = item.findViewById(R.id.Name)
          val TextViewSubject :TextView = item.findViewById(R.id.Subject)
        init {
            CardView.setOnClickListener(::onClick)
        }
         override fun onClick(v: View?) {
             val position = absoluteAdapterPosition
             if(position != RecyclerView.NO_POSITION){
             listener.onItemClick(position,CityList)}
         }
     }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.city_list_item_view, parent, false)
     return CityHolder(view)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.TextViewName.text = CityList[position].name
        holder.TextViewSubject.text =CityList[position].nameSubject

    }

    override fun getItemCount(): Int {
        return CityList.size
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int,List :List<City>)
    }

}


