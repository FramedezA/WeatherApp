package com.example.myapplication


import android.annotation.SuppressLint
import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMain2Binding
import com.google.android.material.dialog.MaterialDialogs
import java.security.AccessController.getContext

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var pref: SharedPreferences? = null
    var pref1: SharedPreferences? = null
    val apprefs = "TABLE"
    val apprefs1 = "TABLE1"
    var lat = ""
    var lon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        pref1 = getSharedPreferences(apprefs1, MODE_PRIVATE)
        lat = pref?.getString("keylat", "0")!!
        lon = pref1?.getString("keylon", "0")!!
        binding.CityList.layoutManager = LinearLayoutManager(this)




    }

    fun saveData(lat: String, lon: String) {
        val editor = pref?.edit()
        editor?.putString("keylat", lat)
        editor?.apply()
        val editor1 = pref1?.edit()
        editor1?.putString("keylon", lon)
        editor1?.apply()
    }


    fun getJson():String {
        val JsonFile: String = applicationContext.assets.open("russian_cities.json")
            .bufferedReader().use { it.readText() }
        return JsonFile
    }
    val ccv= Intent(this@ListActivity, MainActivity::class.java)
    fun GoToWeatherActivity() {

        startActivity(ccv)
    }

    fun onListItemClick( position: Int) {
        GoToWeatherActivity()
    }


}


