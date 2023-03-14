package com.vanquish.despertador.ui.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale

fun showTimePicker(
    parentFragmentManager: FragmentManager,
    textInputPicker: TextInputLayout,
    setHour: Int = 12,
    setMin: Int = 10
) {
    val picker =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(setHour)
            .setMinute(setMin)
            .setNegativeButtonText("Cancelar")
            .setTitleText("Escolha um horário")
            .build()
    picker.show(parentFragmentManager, "tag")

    picker.addOnPositiveButtonClickListener {
        val hour = picker.hour
        val minute = picker.minute
        val time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        textInputPicker.editText?.setText(time)
    }
}