package com.demo.bmcurrencyconverter.utils

class Utils {

    companion object {
        // this function is used to convert currencies using eur rate as base.
        fun convertCurrency(
            amount: String,
            fromCurrency: String,
            toCurrency: String,
            allCurrencies: LinkedHashMap<String, Double>
        ): String {
            return if (amount.isNotEmpty() && amount != "0") {
                val amountDouble: Double = amount.toDouble()
                val eurBasedRateOfFromCurrency: Double = allCurrencies[fromCurrency]!!
                val eurBasedRateofToCurrency: Double = allCurrencies[toCurrency]!!
                val convertedAmount = amountDouble / eurBasedRateOfFromCurrency * eurBasedRateofToCurrency
                String.format("%.6f", convertedAmount)
            } else "0"
        }
    }
}