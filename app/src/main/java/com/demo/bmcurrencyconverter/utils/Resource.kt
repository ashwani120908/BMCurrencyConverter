package com.demo.bmcurrencyconverter.utils

import com.demo.bmcurrencyconverter.utils.Status.SUCCESS
import com.demo.bmcurrencyconverter.utils.Status.ERROR
import com.demo.bmcurrencyconverter.utils.Status.LOADING

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = LOADING, data = data, message = null)
    }
}