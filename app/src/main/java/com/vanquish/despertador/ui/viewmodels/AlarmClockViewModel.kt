package com.vanquish.despertador.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.database.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlarmClockViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    val getAllAlarms: Flow<List<Alarm>> = alarmRepository.getAllAlarms

    suspend fun insert(alarm: Alarm){
        alarmRepository.insert(alarm)
    }

}