package com.demo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemPhoto(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
): Parcelable