package com.vanquish.despertador.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WatchViewModel @Inject constructor() : ViewModel() {

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> get() = _isRunning

    var timer = LocalTime.of(0, 0, 0)

    private val _timerString = MutableStateFlow(timer.toString())
    val timerString: StateFlow<String> get() = _timerString

    private var timerJob: Job? = null

    fun startPauseTimer() {
        if (_isRunning.value) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun resetTimer() {
        _isRunning.value = false
        timer = LocalTime.of(0, 0, 0)
        _timerString.value = "00:00:00"
        timerJob?.cancel()
    }

    private fun startTimer() {
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isRunning.value){
                delay(1000)
                updateTimer()
            }
        }
    }

    private fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    private fun updateTimer() {
        timer = timer.plusSeconds(1)
        _timerString.value = timer.toString()
    }
}