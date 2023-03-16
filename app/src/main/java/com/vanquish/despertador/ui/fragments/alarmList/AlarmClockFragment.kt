package com.vanquish.despertador.ui.fragments.alarmList

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vanquish.despertador.R
import com.vanquish.despertador.databinding.FragmentAlarmClockBinding
import com.vanquish.despertador.ui.adapter.AlarmAdapter
import com.vanquish.despertador.ui.viewmodels.AlarmClockViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FragmentScoped
@RequiresApi(Build.VERSION_CODES.O)
class AlarmClockFragment : Fragment() {

    private lateinit var binding: FragmentAlarmClockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmClockBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarmClockViewModel: AlarmClockViewModel by viewModels()
        val navController = findNavController()

        binding.fabNewAlarm.setOnClickListener {
            navController.navigate(R.id.action_alarmClockFragment_to_newAlarmFragment)
        }

        lifecycleScope.launch {
            alarmClockViewModel.getAllAlarms.collect { alarmList ->
                binding.recyclerViewAlarms.adapter = AlarmAdapter(
                    alarms = alarmList,
                    onLongPress = {
                        val dialog = AlertDialog.Builder(context)
                        dialog
                            .setMessage("Você realmente deseja deletar o alarme: ${it.label}")
                            .setPositiveButton("Apagar") { _, _ ->
                                lifecycleScope.launch {
                                    alarmClockViewModel.deleteAlarm(it)
                                }
                            }
                            .setNegativeButton("Cancelar"){ _, _ -> }
                            .create().show()
                    },
                    onClick = {
                        alarmClockViewModel.setOnClickCardAlarm(it, requireContext(), navController)
                    }
                )
            }
        }


    }

}