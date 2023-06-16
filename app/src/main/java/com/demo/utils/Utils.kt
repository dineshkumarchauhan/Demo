package com.demo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.demo.BuildConfig
import com.demo.R
import com.demo.Controller
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.util.*


/** -------- HIDE KEYBOARD -------- */
@SuppressLint("ServiceCast")
fun hideSoftKeyBoard() = try {
    (Controller.context?.get() as Activity).apply {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Open Soft Keyboard
 * */
fun View.openKeyboard() = try {
    (Controller.context?.get() as Activity).apply {
        postDelayed({
            val inputMethodManager: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            this@openKeyboard.requestFocus()
            inputMethodManager.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
        }, 200)
    }
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * Has Network Available
 * */
fun hasNetwork(): Boolean {
    Controller.context?.get()?.let {
        val connectivityManager =
            it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } ?: return false
}




/**
 * Show Snack Bar
 * */
fun showSnackBar(string: String) = try {
    Controller.context?.get()?.let { context ->
        var message = string
        if (message.contains("Unable to resolve host"))
            message = context.getString(STRING_ALIAS.no_internet_connection)
        Snackbar.make(
            (context as Activity).findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).apply {
            setBackgroundTint(ContextCompat.getColor(context, COLOR_ALIAS.app_color))
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(ContextCompat.getColor(context, COLOR_ALIAS._ffffff))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            show()
        }
    }
} catch (e: Exception) {
    e.printStackTrace()
}



/**
 * Load Image
 * */
@SuppressLint("CheckResult")
fun ImageView.loadImage(
    url: () -> String,
    errorPlaceHolder: () -> Int = { R.drawable.ic_place_holder }
) = try {
    val circularProgressDrawable = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    load(if (url().startsWith("http")) url() else File(url())) {
        placeholder(circularProgressDrawable)
        crossfade(true)
        error(errorPlaceHolder())
    }
} catch (e: Exception) {
    e.printStackTrace()
}



/**
 * Check Permissions
 * */
fun Context.checkCallPermissions(vararg permission: String, returnData: (Boolean) -> Unit) = try {
    Dexter.withContext(this)
        .withPermissions(*permission)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        returnData(true)
                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            startActivity(this)
                        }
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) = Unit
        })
        .check()
} catch (e: Exception) {
    e.printStackTrace()
}






fun Activity.checkIsTablet(): Boolean {
    val display = (this).windowManager.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    val widthInches = metrics.widthPixels / metrics.xdpi
    val heightInches = metrics.heightPixels / metrics.ydpi
    val diagonalInches = Math.sqrt(
        Math.pow(widthInches.toDouble(), 2.0) + Math.pow(
            heightInches.toDouble(),
            2.0
        )
    )
    return diagonalInches >= 7.0
}


