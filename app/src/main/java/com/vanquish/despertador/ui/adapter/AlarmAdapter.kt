package com.vanquish.despertador.ui.adapter

import android.os.Build
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.vanquish.despertador.R
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.database.models.AlarmNew
import com.vanquish.despertador.databinding.ResItemAlarmBinding
import com.vanquish.despertador.extensions.toHourMinuteFormat
import com.vanquish.despertador.ui.viewmodels.AlarmClockViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

@RequiresApi(Build.VERSION_CODES.O)
class AlarmAdapter(
    private val alarms: List<Alarm>,
    private val onClick: (Alarm) -> Unit = {},
) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    private val alarmStateArray: SparseBooleanArray = SparseBooleanArray()

    inner class ViewHolder(itemView: ResItemAlarmBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val textViewAlarmTitle: TextView
        private val textViewAlarmHour: TextView
        private val switchAlarm: Switch

        init {
            textViewAlarmTitle = itemView.textViewAlarmTitle
            textViewAlarmHour = itemView.textViewAlarmHour
            switchAlarm = itemView.switchAlarm
        }


        fun bind(alarm: Alarm, position: Int, onClick: (Alarm) -> Unit) {
            textViewAlarmTitle.text = alarm.label
            textViewAlarmHour.text = toHourMinuteFormat(alarm.timeString)
            switchAlarm.isChecked = alarmStateArray[position, false]
            switchAlarm.setOnClickListener {
                alarmStateArray.put(position, switchAlarm.isChecked)
            }
            itemView.setOnClickListener {
                onClick(alarm)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val resItemAlarmBinding = ResItemAlarmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(resItemAlarmBinding)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alarms[position], position, onClick)
    }

    fun getAlarmStateArray(): SparseBooleanArray {
        return alarmStateArray
    }

}