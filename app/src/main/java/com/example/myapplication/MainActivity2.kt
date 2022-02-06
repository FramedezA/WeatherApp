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
    val apprefs = "TABLE"
    var city = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.b1.setOnClickListener(::onclb1)
        binding.b2.setOnClickListener(::onclb2)
        binding.b3.setOnClickListener(::onclb3)
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        city = pref?.getString("counter", "0")!!
    }

    fun saveData(res: String) {
        val editor = pref?.edit()
        editor?.putString("counter", res)
        editor?.apply()
    }

    fun inte() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun onclb1(view: View) {
        city = "Moscow"
        this.inte()
        saveData(city)
    }

    fun onclb2(view: View) {
        city = "Rostov-on-Don"
        this.inte()
        saveData(city)
    }
    fun onclb3(view: View){
        city = "Saint Petersburg"
        this.inte()
        saveData(city)
    }
}


