package com.demo.bmcurrencyconverter.network

import com.demo.bmcurrencyconverter.models.HistoryData
import com.demo.bmcurrencyconverter.models.LatestRates
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun getLatestRates(): LatestRates

    @GET("{date}")
    suspend fun getHistoryDates(@Path("date") date: String): HistoryData

}