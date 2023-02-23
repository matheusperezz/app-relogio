package com.vanquish.despertador.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timeString: String,
    val daysOfWeekString: String,
    val isEnabled: Boolean,
    val soundUriString: String,
    val label: String
)