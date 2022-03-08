package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.b1.setOnClickListener(::GoToListActivity)
        var pref: SharedPreferences? = null
        var pref1: SharedPreferences? = null
        val apprefs = "TABLE"
        val apprefs1 = "TABLE1"
        var lat: String
        var lon: String
        pref = getSharedPreferences(apprefs, MODE_PRIVATE)
        pref1 = getSharedPreferences(apprefs1, MODE_PRIVATE)
        lat = pref?.getString("keylat", "0").toString()
        lon = pref1?.getString("keylon", "0").toString()
        val key = "b6ece36feee51af1bff36fc08f70cb7d"
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&appid=$key&units=metric&lang=ru&lon=$lon"
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current,minutely,hourly,alerts&appid=$key&units=metric&lang=ru"
        doAsync {
            val apiName = URL(url).readText()
            val name = JSONObject(apiName).getString("name")
            binding.Name.text = name
            val sdf = SimpleDateFormat("dd.MM")
            val apiResponse = URL(url5days).readText()
            val daily = JSONObject(apiResponse).getJSONArray("daily")

            //день1
            val day1 = daily.getJSONObject(0)
            val main1 = day1.getString("dt").toLong()
            val temp = day1.getJSONObject("temp")
            val tempDay = temp.getString("day")
            val tempNig = temp.getString("night")
            val weather = day1.getJSONArray("weather")
            val desc = weather.getJSONObject(0).getString("description")
            val humidity = day1.getString("humidity")
            val windSpeed = day1.getString("wind_speed")
            val date1 = Date(main1 * 1000)
            val time1 = sdf.format(date1)
            binding.tempDay.text = "Днём $tempDay"
            binding.tempNight.text = "Ночью $tempNig"
            binding.verd.text = desc
            binding.vl.text = "Влажность:$humidity"
            binding.ckvt.text = "ветер $windSpeed м/c"
            binding.date.text = time1
            binding.tv1.text = """
            $time1
            Днём $tempDay
            Ночью $tempNig
            $desc
            влажность $humidity%
            ветер $windSpeed м/с
            """.trimIndent()
            //день2
            val day2 = daily.getJSONObject(1)
            val temp2 = day2.getJSONObject("temp")
            val tempDay2 = temp2.getString("day")
            val tempNig2 = temp2.getString("night")
            val weather2 = day2.getJSONArray("weather")
            val desc2 = weather2.getJSONObject(0).getString("description")
            val humidity2 = day2.getString("humidity")
            val windSpeed2 = day2.getString("wind_speed")
            val main2 = day2.getString("dt").toLong()
            val date2 = Date(main2 * 1000)
            val time2 = sdf.format(date2)
            binding.tv2.text = """
            $time2
            Днём $tempDay2
            Ночью $tempNig2
            $desc2
            влажность $humidity2%
            ветер $windSpeed2 м/с
            """.trimIndent()
            //день3
            val day3 = daily.getJSONObject(2)
            val temp3 = day3.getJSONObject("temp")
            val tempDay3 = temp3.getString("day")
            val tempNig3 = temp3.getString("night")
            val weather3 = day3.getJSONArray("weather")
            val desc3 = weather3.getJSONObject(0).getString("description")
            val humidity3 = day3.getString("humidity")
            val windSpeed3 = day3.getString("wind_speed")
            val main3 = day3.getString("dt").toLong()
            val date3 = Date(main3 * 1000)
            val time3 = sdf.format(date3)
            binding.tv3.text = """
            $time3
            Днём $tempDay3
            Ночью $tempNig3
            $desc3
            влажность $humidity3%
            ветер $windSpeed3 м/с
            """.trimIndent()
            //день4
            val day4 = daily.getJSONObject(3)
            val temp4 = day4.getJSONObject("temp")
            val tempDay4 = temp4.getString("day")
            val tempNig4 = temp4.getString("night")
            val weather4 = day4.getJSONArray("weather")
            val desc4 = weather4.getJSONObject(0).getString("description")
            val humidity4 = day4.getString("humidity")
            val windSpeed4 = day4.getString("wind_speed")
            val main4 = day4.getString("dt").toLong()
            val date4 = Date(main4 * 1000)
            val time4 = sdf.format(date4)
            binding.tv4.text = """
           $time4
           Днём $tempDay4
           Ночью $tempNig4
           $desc4
           влажность $humidity4%
           ветер $windSpeed4 м/с
           """.trimIndent()
            //день5
            val day5 = daily.getJSONObject(4)
            val temp5 = day5.getJSONObject("temp")
            val tempDay5 = temp5.getString("day")
            val tempNig5 = temp5.getString("night")
            val weather5 = day5.getJSONArray("weather")
            val desc5 = weather5.getJSONObject(0).getString("description")
            val humidity5 = day5.getString("humidity")
            val windSpeed5 = day5.getString("wind_speed")
            val main5 = day5.getString("dt").toLong()
            val date5 = Date(main5 * 1000)
            val time5 = sdf.format(date5)
            binding.tv5.text = """
            $time5
            Днём $tempDay5
            Ночью $tempNig5
            $desc5
            влажность $humidity5%
            ветер $windSpeed5 м/с
            """.trimIndent()
            //день6
            val day6 = daily.getJSONObject(5)
            val temp6 = day6.getJSONObject("temp")
            val tempDay6 = temp6.getString("day")
            val tempNig6 = temp6.getString("night")
            val weather6 = day6.getJSONArray("weather")
            val desc6 = weather6.getJSONObject(0).getString("description")
            val humidity6 = day6.getString("humidity")
            val windSpeed6 = day6.getString("wind_speed")
            val main6 = day6.getString("dt").toLong()
            val date6 = Date(main5 * 1000)
            val time6 = sdf.format(date5)
            binding.tv6.text = """
            $time6
            Днём $tempDay6
            Ночью $tempNig6
            $desc6
            влажность $humidity6%
            ветер $windSpeed6 м/с
            """.trimIndent()
            //день7
            val day7 = daily.getJSONObject(6)
            val temp7 = day7.getJSONObject("temp")
            val tempDay7 = temp7.getString("day")
            val tempNig7 = temp7.getString("night")
            val weather7 = day7.getJSONArray("weather")
            val desc7 = weather7.getJSONObject(0).getString("description")
            val humidity7 = day7.getString("humidity")
            val windSpeed7 = day7.getString("wind_speed")
            val main7 = day7.getString("dt").toLong()
            val date7 = Date(main5 * 1000)
            val time7 = sdf.format(date5)
            binding.tv7.text = """
            $time7
            Днём $tempDay7
            Ночью $tempNig7
            $desc7
            влажность $humidity7%
            ветер $windSpeed7 м/с
            """.trimIndent()
        }

    }

    fun GoToListActivity(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)

    }
}




