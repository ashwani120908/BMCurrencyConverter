package com.demo.bmcurrencyconverter.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.NetworkHelper
import com.demo.bmcurrencyconverter.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    lateinit var allCurrencies: LinkedHashMap<String, Double>
    val setSpinnerData = MutableLiveData<Boolean>()
    private val noNetworkError = MutableLiveData<Boolean>()

    fun getLatestRates() = liveData(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            emit(Response.loading(data = null))
            try {
                emit(Response.success(data = mainRepository.getLatestRates()))
            } catch (exception: Exception) {
                emit(Response.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        } else {
            noNetworkError.postValue(true)
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