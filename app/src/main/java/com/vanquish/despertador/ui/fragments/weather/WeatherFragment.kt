package com.vanquish.despertador.ui.fragments.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vanquish.despertador.database.repository.WeatherRepository
import com.vanquish.despertador.databinding.FragmentWeatherBinding
import com.vanquish.despertador.ui.viewmodels.WeatherViewModel
import com.vanquish.despertador.webclient.services.WeatherService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import retrofit2.create

@AndroidEntryPoint
@FragmentScoped
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: WeatherViewModel by viewModels()
        binding.buttonGetWeather.setOnClickListener {
            lifecycleScope.launch {
                viewModel.data.collect { weather ->
                    binding.textViewResponse.text = weather?.main?.temp?.toString()
                }
            }
            viewModel.fetchWeather(51.50634, -0.07379, "78e0e8846506403a80b5bf02979b4d28")
        }
    }

}