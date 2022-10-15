package com.demo.bmcurrencyconverter.network

import javax.inject.Inject


class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getLatestRates() = apiHelper.getLatestRates()

    suspend fun getHistoryData(date: String) = apiHelper.getHistoryData(date)
}