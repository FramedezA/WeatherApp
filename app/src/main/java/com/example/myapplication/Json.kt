package com.example.myapplication

import android.content.Context

class Json {

       fun getTextFile(context: Context): String {
           val JsonFile: String = context.assets.open("russian_cities.json")
               .bufferedReader().use { it.readText() }
            return JsonFile
        }
}