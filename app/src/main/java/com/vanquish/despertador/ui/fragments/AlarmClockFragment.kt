package com.vanquish.despertador.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vanquish.despertador.R
import com.vanquish.despertador.database.models.Alarm
import com.vanquish.despertador.databinding.FragmentAlarmClockBinding
import com.vanquish.despertador.extensions.toHourMinuteFormat
import com.vanquish.despertador.ui.adapter.AlarmAdapter
import com.vanquish.despertador.ui.viewmodels.AlarmClockViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime

@AndroidEntryPoint
@FragmentScoped
class AlarmClockFragment : Fragment() {

    private lateinit var binding: FragmentAlarmClockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmClockBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarmClockViewModel: AlarmClockViewModel by viewModels()

        binding.fabNewAlarm.setOnClickListener {
            // findNavController().navigate(R.id.action_alarmClockFragment_to_newAlarmFragment)
            val localTime = LocalTime.now()
            val daysOfWeek = DayOfWeek.FRIDAY


            lifecycleScope.launch {
                alarmClockViewModel.insert(
                    Alarm(
                        0L,
                        localTime.toString(),
                        daysOfWeek.toString(),
                        false,
                        "",
                        "Ta na hora de acordar"
                    )
                )
            }
        }

        lifecycleScope.launch {
            alarmClockViewModel.getAllAlarms.collect { alarmList ->
                binding.recyclerViewAlarms.adapter = AlarmAdapter(alarmList) {
                    Toast.makeText(
                        requireContext(),
                        "Alarm: ${it.label}\nHour: ${toHourMinuteFormat(it.timeString)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }


}