package com.vanquish.despertador.data.models

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)