package com.demo.mainActivity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.demo.networking.ConnectivityManager

import com.demo.databinding.MainActivityBinding
import com.demo.utils.isTablet

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



        if (!isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }else{
        }

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (!isTablet()) {
            Log.e("TAG ","Tablet");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }else{
            Log.e("TAG ","Phone");
        }

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