package com.vanquish.despertador.ui.fragments.alarmList

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.databinding.FragmentNewAlarmBinding
import com.vanquish.despertador.ui.utils.showTimePicker
import com.vanquish.despertador.ui.viewmodels.AlarmClockViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
@FragmentScoped
class NewAlarmFragment : Fragment() {

    private lateinit var binding: FragmentNewAlarmBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewAlarmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarmClockViewModel: AlarmClockViewModel by viewModels()
        binding.imageButtonStartTimePicker.setOnClickListener {
            showTimePicker(parentFragmentManager, binding.textInputNewAlarmTime)
        }

        binding.buttonNewAlarmCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonNewAlarmSave.setOnClickListener {
            if (binding.textInputNewAlarmName.editText?.text.isNullOrBlank()) {
                binding.textInputNewAlarmName.error = "Campo obrigatório"
            } else if (binding.textInputNewAlarmTime.editText?.text.isNullOrBlank()) {
                binding.textInputNewAlarmTime.error = "Campo obrigatório"
            } else {
                val alarmTime = binding.textInputNewAlarmTime.editText?.text?.toString()!!
                val alarmName = binding.textInputNewAlarmName.editText?.text?.toString()!!
                lifecycleScope.launch {
                    val newAlarm = Alarm(
                        0L,
                        alarmTime,
                        DayOfWeek.FRIDAY.toString(),
                        "",
                        alarmName
                    )

                    alarmClockViewModel.insertAlarm(newAlarm)
                    alarmClockViewModel.setAlarm(requireContext(), newAlarm)
                }
                findNavController().popBackStack()
            }
        }

    }

}