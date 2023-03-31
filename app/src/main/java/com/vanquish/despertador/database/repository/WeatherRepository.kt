package com.vanquish.despertador.database.repository

import com.vanquish.despertador.database.models.WeatherResponse
import com.vanquish.despertador.webclient.services.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val retrofit: Retrofit) {

    suspend fun getWeather(lat: Double, lon: Double, appId: String): Flow<Response<WeatherResponse>> = flow {
        val response = retrofit.create(WeatherService::class.java).getWeather(lat, lon, appId)
        emit(response)
    }.flowOn(Dispatchers.IO)

}