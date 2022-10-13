package com.demo.bmcurrencyconverter.utils

import com.demo.bmcurrencyconverter.utils.Status.ERROR
import com.demo.bmcurrencyconverter.utils.Status.LOADING

data class Response<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Response<T> =
            Response(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Response<T> =
            Response(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): Response<T> =
            Response(status = LOADING, data = data, message = null)
    }
}