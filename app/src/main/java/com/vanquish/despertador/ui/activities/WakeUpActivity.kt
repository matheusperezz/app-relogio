package com.vanquish.despertador.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.vanquish.despertador.databinding.ActivityWakeUpBinding

class WakeUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWakeUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonTurnOff.setOnClickListener {
            finish()
        }

    }

}