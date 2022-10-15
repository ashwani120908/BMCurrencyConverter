package com.demo.bmcurrencyconverter.network

import com.demo.bmcurrencyconverter.models.HistoryData
import com.demo.bmcurrencyconverter.models.LatestRates

interface ApiHelper {

    suspend fun getLatestRates(): LatestRates

    suspend fun getHistoryData(date: String): HistoryData
}