package com.vanquish.despertador.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.vanquish.despertador.data.models.Alarm
import com.vanquish.despertador.databinding.ResItemAlarmBinding
import com.vanquish.despertador.ui.utils.toHourMinuteFormat

@RequiresApi(Build.VERSION_CODES.O)
class AlarmAdapter(
    private val alarms: List<Alarm>,
    private val onLongPress: (Alarm) -> Unit = {},
    private val onClick: (Alarm) -> Unit = {},
) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: ResItemAlarmBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val textViewAlarmTitle: TextView
        private val textViewAlarmHour: TextView


        init {
            textViewAlarmTitle = itemView.textViewAlarmTitle
            textViewAlarmHour = itemView.textViewAlarmHour
        }


        fun bind(alarm: Alarm, position: Int, onClick: (Alarm) -> Unit) {
            textViewAlarmTitle.text = alarm.label
            textViewAlarmHour.text = toHourMinuteFormat(alarm.timeString)

            itemView.setOnClickListener {
                onClick(alarm)
            }
            itemView.setOnLongClickListener {
                onLongPress(alarm)
                true
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

}