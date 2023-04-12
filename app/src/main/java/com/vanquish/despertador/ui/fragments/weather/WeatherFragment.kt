package com.vanquish.despertador.ui.fragments.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vanquish.despertador.databinding.FragmentWeatherBinding
import com.vanquish.despertador.ui.utils.kelvinToCelsiusString
import com.vanquish.despertador.ui.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FragmentScoped
class WeatherFragment : Fragment() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: UserLocation
    private val REQUEST_CODE_LOCATION_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ServiceCast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.data.collect { weather ->
                weather?.let {
                    binding.textViewLocalName.text = it.name
                    binding.textViewTemp.text = kelvinToCelsiusString(it.main.temp)
                    binding.textViewDescription.text = it.weather[0].description
                }
            }
        }

        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = UserLocation()

        requestLocationPermission()


    }

    inner class UserLocation : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude
            viewModel.fetchWeather(latitude, longitude, "78e0e8846506403a80b5bf02979b4d28")
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    private fun requestLocationPermission(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            100f,
            locationListener
        )
    }

}