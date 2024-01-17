package com.vanquish.despertador.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmNew(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timeString: String,
    val daysOfWeekString: String,
    val soundUriString: String,
    val label: String
)