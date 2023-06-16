package com.demo.networking


/**
 * Handle Network State
 * */
sealed class NetworkState<T>{

    /**
     * When Not Call Any API
     * */
    class IDEAL<T>: NetworkState<T>()


    /**
     * When API In Progress
     * */
    class LOADING<T>: NetworkState<T>()


    /**
     * When API Give success and get data from API
     * */
    class SUCCESS<T>(val data: T): NetworkState<T>()


    /**
     * When API Give Error And get Error
     * */
    class ERROR<T>(val error: Throwable): NetworkState<T>()
}