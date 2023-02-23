package com.vanquish.despertador.database.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalTime

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): String {
        return localTime.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalTime(localTimeString: String): LocalTime {
        return LocalTime.parse(localTimeString)
    }

    @TypeConverter
    fun fromSetOfDaysOfWeek(setOfDaysOfWeek: Set<DayOfWeek>): String {
        return setOfDaysOfWeek.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toSetOfDaysOfWeek(daysOfWeekString: String): Set<DayOfWeek> {
        return daysOfWeekString.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    }

}