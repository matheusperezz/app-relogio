package com.vanquish.despertador.ui.utils

fun kelvinToCelsiusString(temp: Double) : String {
    val celsius = temp - 273.15
    return "%.1f °C".format(celsius)
}