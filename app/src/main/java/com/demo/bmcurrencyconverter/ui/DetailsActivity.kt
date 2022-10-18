package com.demo.bmcurrencyconverter.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.bmcurrencyconverter.R
import com.demo.bmcurrencyconverter.databinding.ActivityDetailsBinding
import com.demo.bmcurrencyconverter.ui.adapters.ListAdapter
import com.demo.bmcurrencyconverter.utils.Constants
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsActivityViewModel by viewModels()
    private lateinit var dataBinding: ActivityDetailsBinding
    lateinit var lineGraphView: GraphView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        getIntentData()
        setupUI()
        observeLiveData()
        viewModel.getHistoryData()
    }

    private fun observeLiveData() {
        dataBinding.progressBar.visibility = View.VISIBLE
        dataBinding.progressBarFl.visibility = View.VISIBLE
        viewModel.historyData.observe(this, Observer {
            if (it != null) {
                val adapter = ListAdapter(it)

                dataBinding.historicalDataRv.layoutManager = LinearLayoutManager(this)
                dataBinding.historicalDataRv.adapter = adapter
                adapter.notifyDataSetChanged()
                setGraphView()
                dataBinding.progressBarFl.visibility = View.GONE
            }
        })
        viewModel.networkError.observe(this) {
            Toast.makeText(this, "Network Connection Issue!", Toast.LENGTH_LONG)
        }
    }

    private fun setGraphView() {

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(0.10, viewModel.rates[0]),
                DataPoint(0.20, viewModel.rates[1]),
                DataPoint(0.30, viewModel.rates[2]),
            )
        )
        lineGraphView.animate()
        lineGraphView.viewport.isScrollable = true
        lineGraphView.viewport.isScalable = true
        lineGraphView.viewport.setScalableY(true)
        lineGraphView.viewport.setScrollableY(true)
        series.color = R.color.purple_200
        lineGraphView.addSeries(series)
    }

    private fun getIntentData() {
        val map = intent.getSerializableExtra(Constants.KEY_All_CURRENCY) as HashMap<String, Double>
        viewModel.allCurrencies = LinkedHashMap(map)
        viewModel.fromCurrency = intent.getStringExtra(Constants.KEY_FROM_CURRENCY).toString()
        viewModel.toCurrency = intent.getStringExtra(Constants.KEY_TO_CURRENCY).toString()

    }

    private fun setupUI() {
        dataBinding.historicalDataTv.text = "Historical Data for ${viewModel.fromCurrency} to ${viewModel.toCurrency}"
        dataBinding.otherCurrenciesTv.text = "Conversion Rate ${viewModel.fromCurrency} to other currencies"
        dataBinding.otherCurrenciesRv.layoutManager = LinearLayoutManager(this)
        dataBinding.otherCurrenciesRv.adapter = ListAdapter(viewModel.getPopularCurrencyList())
        lineGraphView = dataBinding.graphView

    }
}