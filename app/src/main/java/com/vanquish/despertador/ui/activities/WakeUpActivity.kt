package com.vanquish.despertador.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanquish.despertador.R
import com.vanquish.despertador.databinding.ActivityWakeUpBinding

class WakeUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWakeUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonFinishWakeUp.setOnClickListener {
            finish()
        }
    }
}