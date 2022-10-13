package com.demo.bmcurrencyconverter.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.Response
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val mainRepository: MainRepository) : ViewModel() {

    lateinit var allCurrencies: LinkedHashMap<String, Double>
    val setSpinnerData = MutableLiveData<Boolean>()


    fun getLatestRates() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
        try {
            emit(Response.success(data = mainRepository.getLatestRates()))
        } catch (exception: Exception) {
            emit(Response.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun setCurrencies() {
        setSpinnerData.value = true

    }

    fun convert(s: String, fromCurrency: String, toCurrency: String): String {
        if (s.isNotEmpty() && s != "0") {
            val amount: Double = s.toDouble()
            val eurBasedRateOfFromCurrency: Double = allCurrencies[fromCurrency]!!
            val eurBasedRateofToCurrency: Double = allCurrencies[toCurrency]!!
            val convertedAmount = amount / eurBasedRateOfFromCurrency * eurBasedRateofToCurrency
            Log.e("TAG", convertedAmount.toString())
            return String.format("%.6f", convertedAmount)
        } else return "0"
    }
}