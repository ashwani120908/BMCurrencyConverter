package com.demo.bmcurrencyconverter.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.NetworkHelper
import com.demo.bmcurrencyconverter.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsActivityViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var historyData: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val dates: MutableList<String> = mutableListOf()
    val rates: MutableList<Double> = mutableListOf()
    lateinit var allCurrencies: LinkedHashMap<String, Double>
    lateinit var fromCurrency: String
    lateinit var toCurrency: String

    fun getHistoryData() {
        setLastThreeDates()
        val list: MutableList<String> = mutableListOf<String>()
        if (networkHelper.isNetworkConnected()) {
            GlobalScope.async {
                for (date in dates) {
                    val historyData = withContext(Dispatchers.IO) { mainRepository.getHistoryData(date) }
                    val rate = Utils.convertCurrency("1", fromCurrency, toCurrency, LinkedHashMap(historyData.rates))
                    list.add(" Date - ${date} \n 1 $fromCurrency = $toCurrency $rate")
                    rates.add(rate.toDouble())
                }
                historyData.postValue(list)
            }
        }
    }

    fun getPopularCurrencyList(): MutableList<String> {
        val list: MutableList<String> = ArrayList()
        list.add("1 $fromCurrency = AED " + Utils.convertCurrency("1", fromCurrency, "AED", allCurrencies))
        list.add("1 $fromCurrency = INR " + Utils.convertCurrency("1", fromCurrency, "INR", allCurrencies))
        list.add("1 $fromCurrency = USD " + Utils.convertCurrency("1", fromCurrency, "USD", allCurrencies))
        list.add("1 $fromCurrency = EUR " + Utils.convertCurrency("1", fromCurrency, "EUR", allCurrencies))
        list.add("1 $fromCurrency = EGP " + Utils.convertCurrency("1", fromCurrency, "EGP", allCurrencies))
        list.add("1 $fromCurrency = AUD " + Utils.convertCurrency("1", fromCurrency, "AUD", allCurrencies))
        list.add("1 $fromCurrency = SGD " + Utils.convertCurrency("1", fromCurrency, "SGD", allCurrencies))
        list.add("1 $fromCurrency = QAR " + Utils.convertCurrency("1", fromCurrency, "QAR", allCurrencies))
        list.add("1 $fromCurrency = SAR " + Utils.convertCurrency("1", fromCurrency, "SAR", allCurrencies))
        list.add("1 $fromCurrency = OMR " + Utils.convertCurrency("1", fromCurrency, "OMR", allCurrencies))
        return list
    }

    fun setLastThreeDates(): List<String> {
        val sdf = SimpleDateFormat("YYYY-MM-dd")
        dates.add(sdf.format(getDaysAgo(0)))
        dates.add(sdf.format(getDaysAgo(1)))
        dates.add(sdf.format(getDaysAgo(2)))
        return dates
    }

    fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar.time
    }

}