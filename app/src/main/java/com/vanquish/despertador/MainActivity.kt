package com.vanquish.despertador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vanquish.despertador.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/*
 *
 *  TODO: Fazer o TimePicker funcionar na tela de Update
 *
 *  TODO: Fazer o alarme tocar um som quando der seu horário
 *
 *  TODO: Permitir a exclusão de alarmes
 *
 *  TODO: Implementar a tela do alarme tocando
 *
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navHostFragment = getNavHostFragment()
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }

    private fun getNavHostFragment(): NavHostFragment =
        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
}