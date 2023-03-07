package com.vanquish.despertador.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timeString: String,
    val daysOfWeekString: String,
    val soundUriString: String,
    val label: String
): Serializable