package com.vanquish.despertador.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.vanquish.despertador.R
import com.vanquish.despertador.databinding.FragmentNewAlarmBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

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

        binding.imageButtonStartTimePicker.setOnClickListener {
            showTimePicker()
        }

        binding.buttonNewAlarmCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showTimePicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .setNegativeButtonText("Cancelar")
                .setTitleText("Escolha um horário")
                .build()
        picker.show(parentFragmentManager, "tag")
    }

}