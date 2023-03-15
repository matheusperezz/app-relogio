package com.vanquish.despertador.ui.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.vanquish.despertador.AlarmReceiver
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.database.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class UpdateAlarmViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    val getAllAlarms: Flow<List<Alarm>> = alarmRepository.getAllAlarms

    fun getAlarm(alarm: Alarm): Flow<Alarm?> = alarmRepository.getAlarm(alarmId = alarm.id)


    suspend fun insertAlarm(alarm: Alarm) {
        alarmRepository.insertAlarm(alarm)
    }

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