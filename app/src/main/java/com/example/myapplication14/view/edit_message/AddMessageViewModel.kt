package com.example.myapplication14.view.edit_message

import androidx.compose.runtime.mutableStateOf
import com.example.myapplication14.common.ext.idFromParameter
import com.example.myapplication14.models.Message
import com.example.myapplication14.navigation.MESSAGE_DEFAULT_ID
import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.HomeService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.view.ProjectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Home view model which will handle all the logic related to HomeView
 */
@HiltViewModel
class AddMessageViewModel @Inject constructor(

    logService: LogService,
    private val homeService: HomeService,
    private val authService: AuthService
) : ProjectViewModel(logService) {

    val message = mutableStateOf(Message())

    fun initialize(messageId: String) {
        launchCatching {
            if (messageId != MESSAGE_DEFAULT_ID) {
                message.value = homeService.getMessage(messageId.idFromParameter()) ?: Message()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        message.value = message.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        message.value = message.value.copy(description = newValue)
    }

    fun onUrlChange(newValue: String) {
        message.value = message.value.copy(url = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        message.value = message.value.copy(dueDate = newDueDate)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        message.value = message.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        message.value = message.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        message.value = message.value.copy(priority = newValue)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedMessage = message.value

            if (editedMessage.id.isBlank()) {
                homeService.saveMessage(editedMessage)
            } else {
                homeService.updateMessage(editedMessage)
            }
        }
        popUpScreen()
    }

//    private fun addMessage(message: Message, popUpScreen: () -> Unit) {
//        val saveTaskTrace = Firebase.performance.newTrace(SAVE_TASK_TRACE)
//        saveTaskTrace.start()
//
//        homeService.addMessage(message) { error ->
//            saveTaskTrace.stop()
//            if (error == null) popUpScreen else onError(error)
//        }
//    }
//
//    private fun updateMessage(message: Message, popUpScreen: () -> Unit) {
//        val updateTaskTrace = Firebase.performance.newTrace(UPDATE_TASK_TRACE)
//        updateTaskTrace.start()
//
//        homeService.updateTask(message) { error ->
//            updateTaskTrace.stop()
//            if (error == null) popUpScreen() else onError(error)
//        }
//    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}

