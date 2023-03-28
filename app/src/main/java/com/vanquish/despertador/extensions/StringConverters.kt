package com.vanquish.despertador.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun toHourMinuteFormat(timeString: String) : String {
    val time = LocalTime.parse(timeString)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return time.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun toHourMinSecFormat(timeString: String) : String {
    val time = LocalTime.parse(timeString)
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return time.format(formatter)
}