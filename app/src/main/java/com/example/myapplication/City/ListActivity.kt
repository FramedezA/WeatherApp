package com.example.myapplication.City


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.DataStructures.City
import com.example.myapplication.databinding.ActivityMain2Binding
import kotlinx.coroutines.*
import java.util.*
class ListActivity : AppCompatActivity(), CityListAdapter.OnItemClickListener {
    lateinit var binding: ActivityMain2Binding
    private lateinit var cityList: List<City>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        startRecyclerView()
        binding.editText.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase(Locale.getDefault())
            val filteredList = Search(cityList).filterWithQuery(query)
            attachAdapter(filteredList)
            toggleRecyclerView(filteredList)
        }
        binding.clearEditTextImageView.setOnClickListener {
            binding.editText.setText("")
        }

    }


    override fun onItemClick(position: Int, List: List<City>) {

        if (Wifi().checkInternetConnection(this)) {
            val lat = List[position].lat
            val lon = List[position].lon
            Preferences(this).saveData(lat, lon)
            Navigator(this).goToMainActivity()

        } else {
            Wifi().noConnection(this)
        }

    }


    fun startRecyclerView() {
        binding.stateTextView.text = "Загрузка"

        CoroutineScope(Job()).launch {
            launch(Dispatchers.Main) {
                setStateOfSearchCard(false)
            }
            cityList = CityManager().getCityList(Json().getTextFile(this@ListActivity))
            launch(Dispatchers.Main) {
                binding.stateTextView.visibility = View.INVISIBLE
                binding.stateTextView.text = "Результатов не найдено"
                setStateOfSearchCard(true)
                attachAdapter(cityList)
                binding.CityList.layoutManager = LinearLayoutManager(this@ListActivity)
            }


        }
    }

        fun setStateOfSearchCard(state: Boolean) {
            binding.editText.isEnabled = state
            binding.clearEditTextImageView.isEnabled = state
        }

        fun attachAdapter(list: List<City>) {
            val CityListAdapter = CityListAdapter(list, this)
            binding.CityList.adapter = CityListAdapter
        }

        fun toggleRecyclerView(cityList: List<City>) {
            if (cityList.isEmpty()) {
                binding.CityList.visibility = View.INVISIBLE
                binding.stateTextView.visibility = View.VISIBLE
            } else {
                binding.CityList.visibility = View.VISIBLE
                binding.stateTextView.visibility = View.INVISIBLE
            }
        }




}