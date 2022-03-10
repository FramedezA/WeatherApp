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
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
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
        binding.b2.setOnClickListener(::reset)
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
            val Temp = JSONObject(apiName).getJSONObject("main").getString("temp").toFloat()
            binding.Name.text = name

            val df = DecimalFormat("#")
            val dfWindSpeed = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            dfWindSpeed.roundingMode = RoundingMode.CEILING
            binding.tempDay.text = df.format(Temp)+"°"
            val sdf = SimpleDateFormat("dd.MM")
            val apiResponse = URL(url5days).readText()
            val daily = JSONObject(apiResponse).getJSONArray("daily")

            //день1
            val day1 = daily.getJSONObject(0)
            val main1 = day1.getString("dt").toLong()
            val temp = day1.getJSONObject("temp")
            val tempDay = temp.getString("day").toFloat()
            val tempNig = temp.getString("night").toFloat()
            val weather = day1.getJSONArray("weather")
            val desc = weather.getJSONObject(0).getString("description")
            val humidity = day1.getString("humidity")
            val windSpeed = day1.getString("wind_speed").toFloat()
            val date1 = Date(main1 * 1000)
            val time1 = sdf.format(date1)
            binding.tempDay.text =df.format(tempDay)+"°"
            binding.verd.text = desc
            binding.tvDate1.text = time1
            binding.tvTempday1.text = df.format(tempDay)+"°"
            binding.tvTempnight1.text = df.format(tempNig)+"°"
            binding.tvDesc1.text = desc
            binding.tvHumididty1.text = humidity +"%"
            binding.tvWindSpeed1.text = dfWindSpeed.format(windSpeed) +"м/c"
            //день2
            val day2 = daily.getJSONObject(1)
            val temp2 = day2.getJSONObject("temp")
            val tempDay2 = temp2.getString("day").toFloat()
            val tempNig2 = temp2.getString("night").toFloat()
            val weather2 = day2.getJSONArray("weather")
            val desc2 = weather2.getJSONObject(0).getString("description")
            val humidity2 = day2.getString("humidity")
            val windSpeed2 = day2.getString("wind_speed").toFloat()
            val main2 = day2.getString("dt").toLong()
            val date2 = Date(main2 * 1000)
            val time2 = sdf.format(date2)
            binding.tvDate2.text = time2
            binding.tvTempday2.text = df.format(tempDay2)+"°"
            binding.tvTempnight2.text = df.format(tempNig2)+"°"
            binding.tvDesc2.text = desc2
            binding.tvHumididty2.text = humidity2 +"%"
            binding.tvWindSpeed2.text = dfWindSpeed.format(windSpeed2) +"м/c"
            //день3
            val day3 = daily.getJSONObject(2)
            val temp3 = day3.getJSONObject("temp")
            val tempDay3 = temp3.getString("day").toFloat()
            val tempNig3 = temp3.getString("night").toFloat()
            val weather3 = day3.getJSONArray("weather")
            val desc3 = weather3.getJSONObject(0).getString("description")
            val humidity3 = day3.getString("humidity")
            val windSpeed3 = day3.getString("wind_speed").toFloat()
            val main3 = day3.getString("dt").toLong()
            val date3 = Date(main3 * 1000)
            val time3 = sdf.format(date3)
            binding.tvDate3.text = time3
            binding.tvTempday3.text = df.format(tempDay3)+"°"
            binding.tvTempnight3.text = df.format(tempNig3)+"°"
            binding.tvDesc3.text = desc3
            binding.tvHumididty3.text = humidity3 +"%"
            binding.tvWindSpeed3.text = dfWindSpeed.format(windSpeed3) +"м/c"
            //день4
            val day4 = daily.getJSONObject(3)
            val temp4 = day4.getJSONObject("temp")
            val tempDay4 = temp4.getString("day").toFloat()
            val tempNig4 = temp4.getString("night").toFloat()
            val weather4 = day4.getJSONArray("weather")
            val desc4 = weather4.getJSONObject(0).getString("description")
            val humidity4 = day4.getString("humidity")
            val windSpeed4 = day4.getString("wind_speed").toFloat()
            val main4 = day4.getString("dt").toLong()
            val date4 = Date(main4 * 1000)
            val time4 = sdf.format(date4)
            binding.tvDate4.text = time4
            binding.tvTempday4.text = df.format(tempDay4)+"°"
            binding.tvTempnight4.text = df.format(tempNig4)+"°"
            binding.tvDesc4.text = desc4
            binding.tvHumididty4.text = humidity4 +"%"
            binding.tvWindSpeed4.text = dfWindSpeed.format(windSpeed4) +"м/c"
            //день5
            val day5 = daily.getJSONObject(4)
            val temp5 = day5.getJSONObject("temp")
            val tempDay5 = temp5.getString("day").toFloat()
            val tempNig5 = temp5.getString("night").toFloat()
            val weather5 = day5.getJSONArray("weather")
            val desc5 = weather5.getJSONObject(0).getString("description")
            val humidity5 = day5.getString("humidity")
            val windSpeed5 = day5.getString("wind_speed").toFloat()
            val main5 = day5.getString("dt").toLong()
            val date5 = Date(main5 * 1000)
            val time5 = sdf.format(date5)
            binding.tvDate5.text = time5
            binding.tvTempday5.text = df.format(tempDay5)+"°"
            binding.tvTempnight5.text = df.format(tempNig5)+"°"
            binding.tvDesc5.text = desc5
            binding.tvHumididty5.text = humidity5 +"%"
            binding.tvWindSpeed5.text = dfWindSpeed.format(windSpeed5) +"м/c"
            //день6
            val day6 = daily.getJSONObject(5)
            val temp6 = day6.getJSONObject("temp")
            val tempDay6 = temp6.getString("day").toFloat()
            val tempNig6 = temp6.getString("night").toFloat()
            val weather6 = day6.getJSONArray("weather")
            val desc6 = weather6.getJSONObject(0).getString("description")
            val humidity6 = day6.getString("humidity")
            val windSpeed6 = day6.getString("wind_speed").toFloat()
            val main6 = day6.getString("dt").toLong()
            val date6 = Date(main6 * 1000)
            val time6 = sdf.format(date6)
            binding.tvDate6.text = time6
            binding.tvTempday6.text = df.format(tempDay6)+"°"
            binding.tvTempnight6.text = df.format(tempNig6)+"°"
            binding.tvDesc6.text = desc6
            binding.tvHumididty6.text = humidity6 +"%"
            binding.tvWindSpeed6.text = dfWindSpeed.format(windSpeed6) +"м/c"
            //день7
            val day7 = daily.getJSONObject(6)
            val temp7 = day7.getJSONObject("temp")
            val tempDay7 = temp7.getString("day").toFloat()
            val tempNig7 = temp7.getString("night").toFloat()
            val weather7 = day7.getJSONArray("weather")
            val desc7 = weather7.getJSONObject(0).getString("description")
            val humidity7 = day7.getString("humidity")
            val windSpeed7 = day7.getString("wind_speed").toFloat()
            val main7 = day7.getString("dt").toLong()
            val date7 = Date(main7 * 1000)
            val time7 = sdf.format(date7)
            binding.tvDate7.text = time7
            binding.tvTempday7.text = df.format(tempDay7)+"°"
            binding.tvTempnight7.text = df.format(tempNig7)+"°"
            binding.tvDesc7.text = desc7
            binding.tvHumididty7.text = humidity7 +"%"
            binding.tvWindSpeed7.text = dfWindSpeed.format(windSpeed7) +"м/c"

        }
        this.getDate()

    }

    fun GoToListActivity(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)

    }
    @SuppressLint("SetTextI18n")
    fun getDate(){
        val sdfDay = SimpleDateFormat("dd")
        val dateDay = sdfDay.format(Date()).toInt()
        val sdfMonth = SimpleDateFormat("MM")
        var dateMonth = sdfMonth.format(Date()).toInt()
        var month = when(dateMonth){
            1 -> "Января"
            2 -> "Февраля"
            3 -> "Марта"
            4 ->  "Апреля"
            5 ->  "Мая"
            6 ->  "Июня"
            7 ->  "Июля"
            8 ->  "Августа"
            9 ->  "Сентября"
            10 ->  "Октября"
            11 ->  "Ноября"
            12 ->  "Декабря"
            else -> ""
        }

        binding.date.text = "$dateDay $month"
    }
    fun reset(view: View){

    }

}




