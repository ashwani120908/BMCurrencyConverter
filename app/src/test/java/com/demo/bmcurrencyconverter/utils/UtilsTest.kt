package com.demo.bmcurrencyconverter.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class UtilsTest {

    var allCurrencies: LinkedHashMap<String, Double> = LinkedHashMap()

    @Before
    fun before(){
        allCurrencies.put("AED", 1.0)
        allCurrencies.put("USD", 2.0)
        allCurrencies.put("SDR", 3.0)
        allCurrencies.put("QAR", 4.0)
        allCurrencies.put("INR", 5.0)
    }
    @Test
    fun convertCurrency() {
        Assert.assertEquals("5.000000", Utils.convertCurrency("1", "AED", "INR", allCurrencies))
        Assert.assertEquals("1.000000", Utils.convertCurrency("1", "AED", "AED", allCurrencies))
        Assert.assertEquals("2.000000", Utils.convertCurrency("1", "AED", "USD", allCurrencies))
        Assert.assertEquals("2.500000", Utils.convertCurrency("1", "USD", "INR", allCurrencies))
        Assert.assertEquals("0.750000", Utils.convertCurrency("1", "QAR", "SDR", allCurrencies))
    }

    @Test
    fun convertCurrencyEmptyAmount() {
        Assert.assertEquals("0", Utils.convertCurrency("0", "AED", "INR", allCurrencies))
    }
}