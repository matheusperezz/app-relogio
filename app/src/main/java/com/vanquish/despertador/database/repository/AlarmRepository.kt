package com.vanquish.despertador.database.repository

import com.vanquish.despertador.database.dao.AlarmDao
import com.vanquish.despertador.database.models.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {

    val getAllAlarms: Flow<List<Alarm>> = alarmDao.getAllAlarms()

    suspend fun insert(alarm: Alarm){
        alarmDao.insert(alarm)
    }

}