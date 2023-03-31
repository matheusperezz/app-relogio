package com.vanquish.despertador.webclient

import com.vanquish.despertador.webclient.services.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInitializer {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }

    val apiService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }

}