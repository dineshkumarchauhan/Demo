package com.demo.mainActivity.di

import android.app.Activity
import com.demo.Controller
import com.demo.mainActivity.MainActivity
import com.demo.networking.HttpStatusCode
import com.demo.utils.changeIntent
import com.demo.utils.hasNetwork
import com.demo.utils.showSnackBar
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Status Code Interceptor
 * */
object NetworkInterceptor {


    /**
     * Status Code Handler
     * */
    val interceptor = Interceptor { chain ->
        var request = chain.request()

        /** Handle Data In Cache */
        request = request.newBuilder().apply {
            header(HttpStatusCode.ACCEPT, HttpStatusCode.APPLICATION_JSON)
            header(HttpStatusCode.CONTENT_TYPE, HttpStatusCode.APPLICATION_JSON)
            method(request.method, request.body)
            if (hasNetwork())
                header(HttpStatusCode.CACHE_CONTROL, "public, max-age=5")
            else
                header(
                    HttpStatusCode.CACHE_CONTROL,
                    "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}"
                )
        }.build()


        /**
         * Handle Api Response
         * */
        val response = chain.proceed(request)
        handleStatueCode(response)

        response
    }


    /**
     * Handle Status Code
     * */
    private fun handleStatueCode(response: Response) {
        //Check Status Code
        when (response.code) {
            //Handle Codes
            HttpStatusCode.UN_AUTHORIZE -> Controller.context?.get()?.apply {
                showSnackBar("User sign out due to security purpose. Please sign in again.")
                (this as Activity).changeIntent<MainActivity>(finishAffinity = true)
            }
        }
    }

}