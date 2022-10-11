package com.demo.bmcurrencyconverter.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.demo.bmcurrencyconverter.R
import com.demo.bmcurrencyconverter.models.LatestRates
import com.demo.bmcurrencyconverter.network.ApiHelper
import com.demo.bmcurrencyconverter.network.RetrofitBuilder
import com.demo.bmcurrencyconverter.utils.Status
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()

    }

    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainActivityViewModel::class.java)
    }

    private fun setupUI() {
    }

    private fun setupObservers() {
        viewModel.getLatestRates().observe(this, Observer {
            it?.let { resource ->
                Log.i("status", resource.status.toString())
                Log.i("data", "data:" + resource.data.toString())
                when (resource.status) {
                    Status.SUCCESS -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        resource.data?.let { latestRates -> onLatestRatesRecieved(latestRates) }
                    }
                    Status.ERROR -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        progressBar.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun onLatestRatesRecieved(latestRates: Call<LatestRates?>) {
        Log.e("aaaa", latestRates.toString())
    }
}