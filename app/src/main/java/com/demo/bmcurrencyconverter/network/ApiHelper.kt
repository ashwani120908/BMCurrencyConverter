package com.demo.bmcurrencyconverter.network

class ApiHelper(private val apiService: ApiService) {

    val ACCESS_KEY = "k99Smh0iJPfjU79ioXZBWGnB4kuyZamv"

    suspend fun getLatestRates() = apiService.getLatestRates(ACCESS_KEY)
}