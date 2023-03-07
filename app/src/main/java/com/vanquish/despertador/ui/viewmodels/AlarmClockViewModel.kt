package com.vanquish.despertador.ui.viewmodels

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.vanquish.despertador.AlarmReceiver
import com.vanquish.despertador.R
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.database.models.AlarmNew
import com.vanquish.despertador.database.repository.AlarmRepository
import com.vanquish.despertador.extensions.toHourMinuteFormat
import com.vanquish.despertador.ui.fragments.alarmList.AlarmClockFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

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
    fun setAlarm(context: Context){
        val alarmManager = context.getSystemService(ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 44)
        }

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

}