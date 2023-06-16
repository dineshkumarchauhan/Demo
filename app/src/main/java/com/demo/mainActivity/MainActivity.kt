package com.demo.mainActivity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.demo.networking.ConnectivityManager

import com.demo.databinding.MainActivityBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }

    private val connectivityManager by lazy { ConnectivityManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        binding.vBottom.isVisible = false
        observeConnectivityManager()
    }


    private fun observeConnectivityManager() = try {
        connectivityManager.observe(this) {
            binding.tvInternet.isVisible = !it
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}