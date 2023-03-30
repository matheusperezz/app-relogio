package com.vanquish.despertador.database.models

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)