package com.vanquish.despertador.ui.fragments.alarmList

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.databinding.FragmentUpdateAlarmBinding
import com.vanquish.despertador.extensions.toHourMinuteFormat
import com.vanquish.despertador.ui.utils.showTimePicker
import com.vanquish.despertador.ui.viewmodels.UpdateAlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import java.io.Serializable
import java.time.DayOfWeek
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@FragmentScoped
@AndroidEntryPoint
class UpdateAlarmFragment : Fragment() {

    private lateinit var binding: FragmentUpdateAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateAlarmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: UpdateAlarmViewModel by viewModels()
        val alarm = requireArguments().getSerializable("Alarm") as Alarm
        bindAlarmData(alarm)

        binding.buttonNewAlarmCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonNewAlarmSave.setOnClickListener {
            val alarmName = binding.textInputNewAlarmName.editText?.text.toString()
            val alarmTime = binding.textInputNewAlarmTime.editText?.text.toString()
            val updatedAlarm = Alarm(
                id = alarm.id,
                timeString = alarmTime,
                daysOfWeekString = DayOfWeek.FRIDAY.toString(),
                soundUriString = "",
                label = alarmName
            )

            lifecycleScope.launch {
                viewModel.updateAlarm(updatedAlarm)
            }
            viewModel.setAlarm(requireContext(), updatedAlarm)
            findNavController().popBackStack()
        }

        binding.imageButtonStartTimePicker.setOnClickListener {
            val (hour, minute) = updateTimePickerInput(binding.textInputNewAlarmTime.editText?.text.toString())
            showTimePicker(
                parentFragmentManager,
                binding.textInputNewAlarmTime,
                hour,
                minute
            )
        }

    }

    private fun bindAlarmData(alarm: Alarm) {
        val editableInstance = Editable.Factory.getInstance()
        binding.textInputNewAlarmName.editText?.text = editableInstance.newEditable(alarm.label)
        binding.textInputNewAlarmTime.editText?.text =
            editableInstance.newEditable(toHourMinuteFormat(alarm.timeString))
    }

    private fun updateTimePickerInput(timeInput: String): Pair<Int, Int> {
        val (hourString, minuteString) = timeInput.split(":")
        val hour = hourString.toInt()
        val minute = minuteString.toInt()
        return Pair(hour, minute)
    }

}