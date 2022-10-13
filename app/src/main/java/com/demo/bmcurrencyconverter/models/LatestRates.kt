package com.demo.bmcurrencyconverter.models

data class LatestRates(
    var base: String? = null,
    var date: String? = null,
    var rates: LinkedHashMap<String, Double>? = null,
    var success: Boolean? = null,
    var timestamp: Int? = null

)
