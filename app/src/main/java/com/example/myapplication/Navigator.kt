package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.City.ListActivity
import com.example.myapplication.Weather.MainActivity

class Navigator(val context: Context) {


    fun goToListActivity() {

        val intent = Intent(context, ListActivity::class.java)
        context.startActivity(intent)

    }
    fun goToMainActivity(){
        val intent = Intent(context,MainActivity::class.java)
        context.startActivity(intent)
    }

}