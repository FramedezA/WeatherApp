package com.example.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var bin: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bin.root)
        bin.b1.setOnClickListener(::GoToListActivity)
        this.getDate()
        weather(
            0, bin.tvDate1, bin.tvTempday1, bin.tvTempnight1, bin.tvDesc1,
            bin.tvHumididty1, bin.tvWindSpeed1
        )
        weather(
            1, bin.tvDate2, bin.tvTempday2, bin.tvTempnight2, bin.tvDesc2,
            bin.tvHumididty2, bin.tvWindSpeed2
        )
        weather(
            2, bin.tvDate3, bin.tvTempday3, bin.tvTempnight3, bin.tvDesc3,
            bin.tvHumididty3, bin.tvWindSpeed3
        )
        weather(
            3, bin.tvDate4, bin.tvTempday4, bin.tvTempnight4, bin.tvDesc4,
            bin.tvHumididty4, bin.tvWindSpeed4
        )
        weather(
            4, bin.tvDate5, bin.tvTempday5, bin.tvTempnight5, bin.tvDesc5,
            bin.tvHumididty5, bin.tvWindSpeed5
        )
        weather(
            5, bin.tvDate6, bin.tvTempday6, bin.tvTempnight6, bin.tvDesc6,
            bin.tvHumididty6, bin.tvWindSpeed6
        )
        weather(
            6, bin.tvDate7, bin.tvTempday7, bin.tvTempnight7, bin.tvDesc7,
            bin.tvHumididty7, bin.tvWindSpeed7
        )
    }

    fun GoToListActivity(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)

    }

    @SuppressLint("SetTextI18n")
    fun getDate() {
        val sdfDay = SimpleDateFormat("dd")
        val dateDay = sdfDay.format(Date()).toInt()
        val sdfMonth = SimpleDateFormat("MM")
        var dateMonth = sdfMonth.format(Date()).toInt()
        var month = when (dateMonth) {
            1 -> "Января"
            2 -> "Февраля"
            3 -> "Марта"
            4 -> "Апреля"
            5 -> "Мая"
            6 -> "Июня"
            7 -> "Июля"
            8 -> "Августа"
            9 -> "Сентября"
            10 -> "Октября"
            11 -> "Ноября"
            12 -> "Декабря"
            else -> ""
        }

        bin.date.text = "$dateDay $month"
    }


    fun weather(
        count: Int,
        a: TextView,
        b: TextView,
        c: TextView,
        d: TextView,
        e: TextView,
        f: TextView
    ) {
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
        val url5days =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&exclude=current,minutely,hourly,alerts&appid=$key&units=metric&lang=ru"
        Name(lat, lon, key)
        doAsync {
            val df = DecimalFormat("#")
            val dfWindSpeed = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            dfWindSpeed.roundingMode = RoundingMode.CEILING
            val sdf = SimpleDateFormat("dd.MM")
            val apiResponse = URL(url5days).readText()
            val daily = JSONObject(apiResponse).getJSONArray("daily")
            val day1 = daily.getJSONObject(count)
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
            a.text = time1
            b.text = df.format(tempDay) + "°"
            c.text = df.format(tempNig) + "°"
            d.text = desc
            e.text = humidity + "%"
            f.text = dfWindSpeed.format(windSpeed) + "м/c"
        }

    }

    fun Name(lt: String, ln: String, Key: String) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lt&appid=$Key&units=metric&lang=ru&lon=$ln"
        doAsync {
            val api = URL(url).readText()
            val apiName = JSONObject(api)
            val name = apiName.getString("name")
            val Temp = apiName.getJSONObject("main").getString("temp").toFloat()
            val weather1 = apiName.getJSONArray("weather").getJSONObject(0)
            val description = weather1.getString("description")
            bin.verd.text = description
            bin.Name.text = name
            val df = DecimalFormat("#")
            df.roundingMode = RoundingMode.CEILING
            bin.tempDay.text = df.format(Temp) + "°"
        }
    }


}




