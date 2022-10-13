package com.demo.bmcurrencyconverter.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.bmcurrencyconverter.network.ApiHelper
import com.demo.bmcurrencyconverter.network.MainRepository
import com.demo.bmcurrencyconverter.ui.MainActivityViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}