package com.demo.bmcurrencyconverter.models

data class HistoryData(
    var success: Boolean? = null,
    var historical: Boolean? = null,
    var base: String? = null,
    var date: String? = null,
    var rates: LinkedHashMap<String, Double>? = null,
    var timestamp: Int? = null

)
