package com.demo.bmcurrencyconverter.models

data class LatestRates(
    var base: String? = null,
    var date: String? = null,
    var rates: Map<String, String>? = null,
    var success: Boolean? = null,
    var timestamp: Int? = null

)
