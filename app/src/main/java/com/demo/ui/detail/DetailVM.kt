package com.demo.ui.detail

import androidx.lifecycle.ViewModel
import com.demo.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {


}