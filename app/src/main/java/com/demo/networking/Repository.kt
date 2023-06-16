package com.demo.networking


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.demo.Controller
import com.demo.databinding.LoaderBinding
import com.demo.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class Repository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    private val mainDispatcher by lazy { Dispatchers.Main }
    var alertDialog: AlertDialog? = null


    /**
     * Call Api
     * */
    suspend fun <T> callApi(
        loader: Boolean = true,
        callHandler: CallHandler<T>
    ) {

        /**
         * Hide Soft Keyboard
         * */
        hideSoftKeyBoard()


        /**
         * Coroutine Exception Handler
         * */
        val coRoutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            mainThread {
                throwable.message.let {
                    hideLoader()
                    callHandler.error(it.getErrorMessage())
                }
            }
        }


        /**
         * No Internet Connection Handler
         * */
        if (!hasNetwork()) {
            Controller.context?.get()?.getString(STRING_ALIAS.no_internet_connection)?.let {
                showSnackBar(it)
                callHandler.error(it)
            }
            return
        }


        /**
         * Call Api
         * */
        CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler + Job()).launch {
            flow {
                emit(callHandler.sendRequest(apiInterface = apiInterface) as Response<*>)
            }.flowOn(ioDispatcher)
                .retryWhen { cause, attempt ->
                    Logger.error("NetworkRetry", "Retry.......  Cause:$cause, Attempt:$attempt")
                    (attempt < HttpStatusCode.RETRY_COUNT) && (cause is IOException)
                }.onStart {
                    callHandler.loading()
                    withContext(mainDispatcher) {
                        if (loader) Controller.context?.get()?.showLoader()
                    }
                }.catch { error ->
                    withContext(mainDispatcher) {
                        hideLoader()
                        callHandler.error(error.getErrorMessage())
                    }
                }.collect { response ->
                    withContext(mainDispatcher) {
                        hideLoader()
                        if (response.isSuccessful)
                            callHandler.success(response as T)
                        else
                            response.errorBody()?.string()
                                ?.let { callHandler.error(it.getErrorMessage()) }
                    }
                }
        }
    }


    /**
     * Show Loader
     * */
    private fun Context.showLoader() {
        if (alertDialog == null) {
            val alert = AlertDialog.Builder(this)
            val binding = LoaderBinding.inflate(LayoutInflater.from(this), null, false)
            alert.setView(binding.root)
            alert.setCancelable(false)
            alertDialog = alert.create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
        }
    }


    /**
     * Hide Loader
     * */
    private fun hideLoader() {
        alertDialog?.dismiss()
        alertDialog = null
    }

}