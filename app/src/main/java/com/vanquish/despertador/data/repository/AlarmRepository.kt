package com.vanquish.despertador.data.repository

import com.vanquish.despertador.data.dao.AlarmDao
import com.vanquish.despertador.data.models.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {

    val getAllAlarms: Flow<List<Alarm>> = alarmDao.getAllAlarms()

    suspend fun insertAlarm(alarm: Alarm){
        alarmDao.insertAlarm(alarm)
    }

    suspend fun updateAlarm(alarm: Alarm){
        alarmDao.updateAlarm(alarm)
    }

    suspend fun deleteAlarm(alarm: Alarm){
        alarmDao.deleteAlarm(alarm)
    }

    fun getAlarm(alarmId: Long): Flow<Alarm?> {
        return alarmDao.getAlarm(alarmId)
    }

}