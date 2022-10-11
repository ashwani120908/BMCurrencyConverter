package com.demo.bmcurrencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getLatestRates() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getLatestRates()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}