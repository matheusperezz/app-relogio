package com.vanquish.despertador.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vanquish.despertador.database.models.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("SELECT * FROM Alarm")
    fun getAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM Alarm WHERE id = :alarmId")
    fun getAlarm(alarmId: Long): Flow<Alarm?>

}
