package com.example.myapplication


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var pref: SharedPreferences? = null
    var pref1 :SharedPreferences? = null
    val apprefs = "TABLE"
    val apprefs1 = "TABLE1"
    var lat = ""
    var lon = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.b1.setOnClickListener(::onclb1)
        binding.b2.setOnClickListener(::onclb2)
        binding.b3.setOnClickListener(::onclb3)
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        pref1 = getSharedPreferences(apprefs1, MODE_PRIVATE)
        lat = pref?.getString("keylat","0")!!
        lon = pref1?.getString("keylon","0")!!
    }

    fun saveData(res: String) {
        val editor = pref?.edit()
        editor?.putString("keylat", res)
        editor?.apply()
    }
    fun saveData1(res: String){
        val editor = pref1?.edit()
        editor?.putString("keylon",res)
        editor?.apply()
    }

    fun inte() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun onclb1(view: View) {
        lat = "55.75"
        lon ="37.61"
        this.inte()
        saveData(lat)
        saveData1(lon)
    }
    fun onclb2(view: View) {
        lat = "47.2313"
        lon ="39.7233"
        this.inte()
        saveData(lat)
        saveData1(lon)
    }
    fun onclb3(view: View){
        this.inte()
    }
}


