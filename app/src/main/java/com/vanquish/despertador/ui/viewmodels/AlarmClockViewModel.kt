package com.vanquish.despertador.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.database.models.AlarmNew
import com.vanquish.despertador.database.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlarmClockViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    val getAllAlarms: Flow<List<Alarm>> = alarmRepository.getAllAlarms

    fun getAlarm(alarm: Alarm): Flow<Alarm?> = alarmRepository.getAlarm(alarmId = alarm.id)


    suspend fun insertAlarm(alarm: Alarm){
        alarmRepository.insertAlarm(alarm)
    }

    suspend fun updateAlarm(alarm: Alarm){
        alarmRepository.updateAlarm(alarm)
    }

}