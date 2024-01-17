package com.vanquish.despertador.ui.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.vanquish.despertador.AlarmReceiver
import com.vanquish.despertador.R
import com.vanquish.despertador.data.models.Alarm
import com.vanquish.despertador.data.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AlarmClockViewModel @Inject constructor(private val alarmRepository: AlarmRepository) :
    ViewModel() {

    val getAllAlarms: Flow<List<Alarm>> = alarmRepository.getAllAlarms

    fun getAlarm(alarm: Alarm): Flow<Alarm?> = alarmRepository.getAlarm(alarmId = alarm.id)


    suspend fun insertAlarm(alarm: Alarm) {
        alarmRepository.insertAlarm(alarm)
    }

    suspend fun updateAlarm(alarm: Alarm) {
        alarmRepository.updateAlarm(alarm)
    }

    suspend fun deleteAlarm(alarm: Alarm){
        alarmRepository.deleteAlarm(alarm)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickCardAlarm(alarm: Alarm, context: Context, navController: NavController) {

        val bundle = Bundle()
        bundle.putSerializable("Alarm", alarm)

        navController.navigate(
            R.id.action_alarmClockFragment_to_updateAlarmFragment,
            bundle
        )

//        val mediaPlayer = MediaPlayer.create(context, R.raw.train_crossing_bell)
//        mediaPlayer.setOnCompletionListener {
//            mediaPlayer.release()
//        }
//        mediaPlayer.start()
    }


    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(context: Context, alarm: Alarm) {
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