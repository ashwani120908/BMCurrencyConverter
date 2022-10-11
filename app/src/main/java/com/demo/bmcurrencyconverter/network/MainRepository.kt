package com.demo.bmcurrencyconverter.network


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getLatestRates() = apiHelper.getLatestRates()
}