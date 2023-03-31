package com.vanquish.despertador.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanquish.despertador.database.models.WeatherResponse
import com.vanquish.despertador.database.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _data = MutableStateFlow<WeatherResponse?>(null)
    val data: StateFlow<WeatherResponse?> = _data

    fun fetchWeather(
        lat: Double,
        lon: Double,
        appId: String
    ){
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeather(lat, lon, appId).first()
                if (response.isSuccessful){
                    _data.emit(response.body())
                } else {
                    // Tratar erro
                }
            } catch (e: Exception){
                Log.e("REQUISITION ERROR", e.toString())
            }
        }
    }

}