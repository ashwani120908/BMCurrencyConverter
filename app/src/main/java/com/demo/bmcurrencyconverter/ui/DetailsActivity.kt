package com.demo.bmcurrencyconverter.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.bmcurrencyconverter.R
import com.demo.bmcurrencyconverter.databinding.ActivityDetailsBinding
import com.demo.bmcurrencyconverter.ui.adapters.ListAdapter
import com.demo.bmcurrencyconverter.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsActivityViewModel by viewModels()
    private lateinit var dataBinding: ActivityDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        getIntentData()
        setupUI()
        observeLiveData()
        viewModel.getHistoryData()
    }

    private fun observeLiveData() {

        viewModel.historyData.observe(this, Observer {
            if (it != null) {
                dataBinding.historicalDataRv.layoutManager = LinearLayoutManager(this)
                dataBinding.historicalDataRv.adapter = ListAdapter(it)
            }
        })

    }

    private fun getIntentData() {
        val map = intent.getSerializableExtra(Constants.KEY_All_CURRENCY) as HashMap<String, Double>
        viewModel.allCurrencies = LinkedHashMap(map)
        viewModel.fromCurrency = intent.getStringExtra(Constants.KEY_FROM_CURRENCY).toString()
        viewModel.toCurrency = intent.getStringExtra(Constants.KEY_TO_CURRENCY).toString()

    }

    private fun setupUI() {
        dataBinding.otherCurrenciesRv.layoutManager = LinearLayoutManager(this)
        dataBinding.otherCurrenciesRv.adapter = ListAdapter(viewModel.getPopularCurrencyList())

    }
}