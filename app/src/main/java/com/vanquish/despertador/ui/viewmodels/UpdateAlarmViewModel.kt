package com.vanquish.despertador.ui.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.vanquish.despertador.AlarmReceiver
import com.vanquish.despertador.data.models.Alarm
import com.vanquish.despertador.data.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class UpdateAlarmViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    suspend fun updateAlarm(alarm: Alarm) {
        alarmRepository.updateAlarm(alarm)
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(context: Context, alarm: Alarm){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val regex = "(\\d+):(\\d+)".toRegex()
        val matchResult = regex.matchEntire(alarm.timeString)
        val hour = matchResult?.groupValues?.get(1)?.toInt()
        val minute = matchResult?.groupValues?.get(2)?.toInt()


        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour!!)
            set(Calendar.MINUTE, minute!!)
        }

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

}