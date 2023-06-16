package com.demo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class Controller : Application(), Application.ActivityLifecycleCallbacks {


    /**
     * Static Keywords
     * */
    companion object {
        @JvmStatic
        var context: WeakReference<Context>? = null
    }


    /**
     * On Create
     * */
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        registerActivityLifecycleCallbacks(this)

    }


    /**
     * Activity Life Cycle
     * */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        context = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) {
        context = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit
}