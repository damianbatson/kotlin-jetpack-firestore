package com.example.myapplication14.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication14.common.snackbar.SnackbarManager
import com.example.myapplication14.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.myapplication14.services.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Set of widgets/views which will be used throughout the application.
 * This is used to increase the code usability.
 */

open class ProjectViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}