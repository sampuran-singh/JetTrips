package com.example.jettrips.test

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.jettrips.R
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    private val viewModel: TestViewModel by viewModels()
    private lateinit var textView: TextView
    private lateinit var countryList: TextView
    private lateinit var timeUpdate: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.tv_first)
        countryList = findViewById(R.id.tv_country)
        timeUpdate = findViewById(R.id.tv_time_update)


        lifecycleScope.launch {
            viewModel.dataFlow.collect { data ->
                // Update the UI with the new data
                textView.text = data
            }


        }

        lifecycleScope.launch {
            viewModel.countryFlow.collect{
                countryList.text = it
            }
        }

        lifecycleScope.launch {
            viewModel.weatherData.collect {
                timeUpdate.text = it
            }
        }


        // Trigger the ViewModel to load data
//        viewModel.loadData()
        viewModel.mainSuperTest()
        viewModel.getNameOfCountry()
    }
}