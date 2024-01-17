package com.vanquish.despertador.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanquish.despertador.data.converters.Converters
import com.vanquish.despertador.data.dao.AlarmDao
import com.vanquish.despertador.data.models.Alarm

@Database(entities = [Alarm::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao


}