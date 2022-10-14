package com.demo.bmcurrencyconverter.ui

import androidx.lifecycle.ViewModel
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.NetworkHelper
import com.demo.bmcurrencyconverter.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsActivityViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {


    lateinit var allCurrencies: LinkedHashMap<String, Double>
    lateinit var fromCurrency: String
    lateinit var toCurrency: String


    fun getPopularcurrencyList(): MutableList<String> {
        var list: MutableList<String> = ArrayList()
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
}