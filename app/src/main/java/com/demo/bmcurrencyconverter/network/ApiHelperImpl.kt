package com.demo.bmcurrencyconverter.network

import com.demo.bmcurrencyconverter.models.LatestRates
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    companion object {
        const val BASE_URL = "https://api.apilayer.com/fixer/"

        const val ACCESS_KEY = "k99Smh0iJPfjU79ioXZBWGnB4kuyZamv"
    }

    override suspend fun getLatestRates(): LatestRates =
        apiService.getLatestRates(ACCESS_KEY)

}