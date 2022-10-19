package com.demo.bmcurrencyconverter.network

import com.demo.bmcurrencyconverter.models.HistoryData
import com.demo.bmcurrencyconverter.models.LatestRates
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    companion object {
        const val BASE_URL = "https://api.apilayer.com/fixer/"

        const val ACCESS_KEY = "6zL4EF6R1gEu01ZLb4CZ5cIFeoErzqC1"
    }

    override suspend fun getLatestRates(): LatestRates =
        apiService.getLatestRates()

    override suspend fun getHistoryData(date: String): HistoryData =
        apiService.getHistoryDates( date)

}