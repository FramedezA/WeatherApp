package com.example.myapplication


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMain2Binding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class ListActivity : AppCompatActivity(), CityListAdapter.OnItemClickListener {
    lateinit var binding: ActivityMain2Binding
    var pref: SharedPreferences? = null
    private lateinit var CityList: List<City>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        startRecyclerView()
        binding.editText.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase(Locale.getDefault())
            filterWithQuery(query)

        }
        binding.clearEditTextImageView.setOnClickListener {
            binding.editText.setText("")

        }

    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            val filteredList: List<City> = onFilterChanged(query)
            attachAdapter(filteredList)
            toggleRecyclerView(filteredList)
        } else if (query.isEmpty()) {
            attachAdapter(CityList)
        }
    }

    private fun toggleRecyclerView(sportsList: List<City>) {
        if (sportsList.isEmpty()) {
            binding.CityList.visibility = View.INVISIBLE
            binding.stateTextView.visibility = View.VISIBLE
        } else {
            binding.CityList.visibility = View.VISIBLE
            binding.stateTextView.visibility = View.INVISIBLE
        }
    }

    private fun attachAdapter(list: List<City>) {
        val CityListAdapter = CityListAdapter(list, this)
        binding.CityList.adapter = CityListAdapter
    }

    private fun onFilterChanged(filterQuery: String): List<City> {
        val filteredList = ArrayList<City>()
        for (currentCity in CityList) {
            if (currentCity.name.lowercase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(currentCity)
            }
        }
        return filteredList
    }


    override fun onItemClick(position: Int, List: List<City>) {
        if(Wifi().isOnline(this)){
        pref = getSharedPreferences("TABLE", MODE_PRIVATE)
        val lat = List[position].lat
        val lon = List[position].lon
        Preferences().saveData(lat, lon, pref)
        GoToWeatherActivity()
        }
        else{
            val toast = Toast.makeText(this, "Нет подключения к интернету".trimMargin(), Toast.LENGTH_SHORT)
            toast.show()
        }

    }


    fun startRecyclerView() {
        val recycler = binding.CityList
        binding.stateTextView.text = "Загрузка"

        CoroutineScope(Job()).launch {
            runOnUiThread {
                binding.editText.isEnabled = false
                binding.clearEditTextImageView.isEnabled = false
            }
            CityList = CityManager().getCityList(getJson())
            runOnUiThread {
                binding.stateTextView.visibility = View.INVISIBLE
                binding.stateTextView.text = "Результатов не найдено"
                binding.editText.isEnabled = true
                binding.clearEditTextImageView.isEnabled = true
                attachAdapter(CityList)

            }
        }
        recycler.layoutManager = LinearLayoutManager(this)
    }


    fun getJson(): String {
        val JsonFile: String = applicationContext.assets.open("russian_cities.json")
            .bufferedReader().use { it.readText() }
        return JsonFile
    }


    fun GoToWeatherActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}


