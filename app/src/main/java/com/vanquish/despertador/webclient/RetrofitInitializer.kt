package com.vanquish.despertador.webclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Exemplo: https://api.openweathermap.org/data/2.5/weather?lat=-23.50552&lon=-46.35928&appid=78e0e8846506403a80b5bf02979b4d28
class RetrofitInitializer {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}