package com.example.myapplication14.view.home

import androidx.compose.runtime.mutableStateOf
import com.example.myapplication14.models.Message
import com.example.myapplication14.navigation.ADD_MESSAGE_SCREEN
import com.example.myapplication14.navigation.HOME_SCREEN
import com.example.myapplication14.navigation.MESSAGE_ID
import com.example.myapplication14.navigation.SETTINGS_SCREEN
import com.example.myapplication14.services.ConfigurationService
import com.example.myapplication14.services.HomeService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.view.ProjectViewModel
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Home view model which will handle all the logic related to HomeView
 */
@HiltViewModel
class HomeViewModel @Inject constructor(

    logService: LogService,
    private val homeService: HomeService,
    private val configurationService: ConfigurationService
) : ProjectViewModel(logService) {

    val hasEditIcon = mutableStateOf(true)
    val options = mutableStateOf<List<String>>(listOf())

    val messages = homeService.messages

    /**
     * Update the message value as user types
     */

    fun loadTaskOptions() {
        hasEditIcon.value = configurationService.hasEditIcon
        options.value = TaskActionOption.getOptions(hasEditIcon.value)
    }

    fun onTaskCheckChange(message: Message) {
        launchCatching {
            homeService.updateMessage(message.copy(completed = !message.completed))
        }
    }

    fun onTaskActionClick(openScreen: (String) -> Unit, message: Message, action: String) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> onMessageEditClick(openScreen, message)
            TaskActionOption.ToggleFlag -> onFlagTaskClick(message)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(message)
        }
    }

    fun onMessageEditClick(openScreen: (String) -> Unit, message: Message) {
        Firebase.analytics.logEvent(EDIT_MESSAGE_EVENT, null)
        openScreen("$ADD_MESSAGE_SCREEN?$MESSAGE_ID={${message.id}}")
    }

    private fun onFlagTaskClick(message: Message) {
        launchCatching {
            homeService.updateMessage(message.copy(flag = !message.flag))
        }
    }

    private fun onDeleteTaskClick(message: Message) {
        launchCatching {
            homeService.deleteMessage(message.id)
        }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(ADD_MESSAGE_SCREEN)


    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
        private const val EDIT_MESSAGE_EVENT = "edit_message_click"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}

