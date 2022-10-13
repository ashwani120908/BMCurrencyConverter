package com.demo.bmcurrencyconverter.utils

class Utils {
    public val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }
    companion object{

    }
}