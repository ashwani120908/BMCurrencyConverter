package com.demo.bmcurrencyconverter.network

import com.demo.bmcurrencyconverter.models.LatestRates
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun getLatestRates(@Query("apikey") key: String): LatestRates?

}