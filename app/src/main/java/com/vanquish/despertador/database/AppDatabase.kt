package com.vanquish.despertador.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanquish.despertador.database.converters.Converters
import com.vanquish.despertador.database.dao.AlarmDao
import com.vanquish.despertador.database.models.Alarm

@Database(entities = [Alarm::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

}