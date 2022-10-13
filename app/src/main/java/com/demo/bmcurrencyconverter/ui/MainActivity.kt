package com.demo.bmcurrencyconverter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.bmcurrencyconverter.R
import com.demo.bmcurrencyconverter.databinding.ActivityMainBinding
import com.demo.bmcurrencyconverter.models.LatestRates
import com.demo.bmcurrencyconverter.network.ApiHelper
import com.demo.bmcurrencyconverter.network.RetrofitBuilder
import com.demo.bmcurrencyconverter.ui.base.ViewModelFactory
import com.demo.bmcurrencyconverter.utils.Status


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var fromSpinner: AppCompatSpinner
    private lateinit var toSpinner: AppCompatSpinner
    private lateinit var switchButtonIv: AppCompatImageView
    private lateinit var fromEt: AppCompatEditText
    private lateinit var toEt: AppCompatEditText
    private lateinit var textWatcher: TextWatcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupUI()
        getLatestRates()
    }

    private fun getLatestRates() {
        viewModel.getLatestRates().observe(this, Observer {
            it?.let { response ->
                Log.i("status", response.status.toString())
                Log.i("data", "data:" + response.data.toString())
                when (response.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        response.data?.let { latestRates -> onLatestRatesRecieved(latestRates) }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Log.e("TAG", it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setDefaultValue() {
        fromSpinner.setSelection(viewModel.allCurrencies.keys.indexOf("EUR"))
        toSpinner.setSelection(viewModel.allCurrencies.keys.indexOf("AED"))
        fromEt.setText("1")
        toEt.setText(viewModel.allCurrencies.get("AED").toString())
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainActivityViewModel::class.java)
    }

    private fun setupUI() {
        progressBar = dataBinding.progressBar
        fromSpinner = dataBinding.fromSpinner
        toSpinner = dataBinding.toSpinner
        switchButtonIv = dataBinding.switchIv
        fromEt = dataBinding.fromEt
        toEt = dataBinding.toEt

    }

    private fun setResponseData() {

        viewModel.setSpinnerData.observe(this) {

            val adapter =
                ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    viewModel.allCurrencies.keys.toList()
                )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            fromSpinner.adapter = adapter
            toSpinner.adapter = adapter
        }
    }


    private fun onLatestRatesRecieved(latestRates: LatestRates) {
        viewModel.allCurrencies = latestRates.rates!!
        viewModel.setCurrencies()
        setResponseData()
        setDefaultValue()
        setListeners()
        addTextWatchers()
    }

    private fun setListeners() {


        fromSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calculateCurrency()
            }

        }

        toSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calculateCurrency()
            }

        }

        switchButtonIv.setOnClickListener {

            val fromId = fromSpinner.selectedItemId.toInt()
            val toId = toSpinner.selectedItemId.toInt()

            fromSpinner.setSelection(toId)
            toSpinner.setSelection(fromId)

            calculateCurrency()
        }


    }

    private fun addTextWatchers() {
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        fromEt.addTextChangedListener(textWatcher)
        toEt.addTextChangedListener(textWatcher)
    }

    private fun calculateCurrency() {
        toEt.removeTextChangedListener(textWatcher)
        fromEt.removeTextChangedListener(textWatcher)
        if (fromSpinner.selectedItem != null && fromSpinner.selectedItem != null) {
            toEt.setText(
                viewModel.convert(
                    fromEt.text.toString(),
                    fromSpinner.selectedItem.toString(),
                    toSpinner.selectedItem.toString()
                )
            )
            addTextWatchers()
        }

    }
}