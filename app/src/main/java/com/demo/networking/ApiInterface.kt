package com.demo.networking


import com.demo.model.Items
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET(Products)
    suspend fun getPhotos(
    ): Response<Items>

}