package com.vanquish.despertador.ui.fragments.timer

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vanquish.despertador.databinding.FragmentWatchBinding
import com.vanquish.despertador.ui.utils.toHourMinSecFormat
import com.vanquish.despertador.ui.viewmodels.WatchViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalTime

@AndroidEntryPoint
@FragmentScoped
class WatchFragment : Fragment() {

    private lateinit var binding: FragmentWatchBinding

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
        val viewModel: WatchViewModel by viewModels()

        binding.buttonTimerStart.setOnClickListener {
            viewModel.startPauseTimer()
        }

        binding.buttonTimerZero.setOnClickListener {
            viewModel.resetTimer()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isRunning.collect {
                binding.buttonTimerStart.text = if (it) "Pause" else "Start"
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.timerString.collect {
                binding.textViewTimer.text = it
            }
        }
    }
}