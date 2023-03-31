package com.vanquish.despertador.extensions

fun kelvinToCelsiusString(temp: Double) : String {
    val celsius = temp - 273.15
    return "%.1f °C".format(celsius)
}