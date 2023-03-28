package com.vanquish.despertador.ui.fragments

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.vanquish.despertador.databinding.FragmentWatchBinding
import com.vanquish.despertador.extensions.toHourMinSecFormat
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import java.time.LocalTime

@AndroidEntryPoint
@FragmentScoped
class WatchFragment : Fragment() {

    private lateinit var binding: FragmentWatchBinding

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        var timer: LocalTime = LocalTime.of(0, 0, 0)
        @RequiresApi(Build.VERSION_CODES.O)
        var timerString = timer.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler()

        val runnable = object : Runnable {
            override fun run() {
                addOneSecondOnTimer()
                handler.postDelayed(this, 1000)
            }
        }


        binding.buttonTimerStart.setOnClickListener {
            handler.postDelayed(runnable, 1000)
        }

        binding.buttonTimerPause.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        binding.buttonTimerZero.setOnClickListener {
            zeroTimer()
            handler.removeCallbacks(runnable)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addOneSecondOnTimer(){
        timer = timer.plusSeconds(1)
        timerString = timer.toString()
        binding.textViewTimer.text = toHourMinSecFormat(timerString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun zeroTimer(){
        timer = LocalTime.of(0, 0, 0)
        timerString = timer.toString()
        binding.textViewTimer.text = timerString
    }
}