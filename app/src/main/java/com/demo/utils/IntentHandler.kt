package com.demo.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

/** Share all intent data into a all apps */
fun Context.openShareIntent(data: String) = try {
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, data)
        startActivity(Intent.createChooser(this, getString(STRING_ALIAS.app_name)))
    }
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Change Intent Data
 * */
inline fun <reified T> Activity.changeIntent(
    finish: Boolean = false,
    finishAffinity: Boolean = false
) {
    Intent(this, T::class.java).apply {
        startActivity(this)
        if (finish) finish()
        if (finishAffinity) finishAffinity()
    }
}
