package com.demo.utils

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.demo.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * Show Alert Dialog
 * */
fun Activity.showDialog(
    title: String,
    message: String,
    positiveString: String = "",
    negativeString: String = "",
    callBack: () -> Unit
) {
    MaterialAlertDialogBuilder(this).apply {
        setCancelable(false)
        setFinishOnTouchOutside(false)
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveString) { dialog, _ ->
            dialog.dismiss()
            callBack()
        }
        setNegativeButton(negativeString) { dialog, _ ->
            dialog.dismiss()
        }
        create()
        show()
    }
}


/**
 * Show Error Handler
 * */
fun Activity.showExitSnackBar() {
    try {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.are_you_sure_want_to_exit),
            Snackbar.LENGTH_LONG
        ).apply {
            setBackgroundTint(ContextCompat.getColor(this@showExitSnackBar, R.color.app_color))
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(ContextCompat.getColor(this@showExitSnackBar, R.color._ffffff))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            setAction(STRING_ALIAS.yes) { finishAffinity() }
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}




/**
 * Generic Bottom Sheet
 * */
fun <T> Context.showBottomAlert(
    viewBinding: ViewBinding,
    callBack: (binding: T, dialog: BottomSheetDialog) -> Unit
) {
    BottomSheetDialog(this).apply {
        setContentView(viewBinding.root)
        setCanceledOnTouchOutside(false)
        show()
        @Suppress("UNCHECKED_CAST")
        callBack(viewBinding as T, this)
    }
}