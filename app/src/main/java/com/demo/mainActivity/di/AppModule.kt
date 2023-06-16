package com.blacqhorse.di

import android.content.Context

import com.demo.BuildConfig
import com.demo.mainActivity.di.NetworkInterceptor
import com.demo.networking.ApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Save Data in Cache
     * */
    @Provides
    @Singleton
    fun cache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, (5 * 1024 * 1024).toLong())


    /**
     * Provide Ok Http Client
     * */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkInterceptor.interceptor)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .build()
    }


    /**
     * Gson Provider
     * */
    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().create()


    /**
     * Provide Retrofit Instance
     * */
    @Provides
    @Singleton
    fun providesRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()


    /**
     * Provide Api Params Interface Instance
     * */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

}