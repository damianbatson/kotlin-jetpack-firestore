package com.example.myapplication14.view.edit_message

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication14.common.ActionToolbar
import com.example.myapplication14.common.BasicField
import com.example.myapplication14.common.CardSelector
import com.example.myapplication14.common.RegularCardEditor
import com.example.myapplication14.common.ext.card
import com.example.myapplication14.common.ext.fieldModifier
import com.example.myapplication14.common.ext.spacer
import com.example.myapplication14.common.ext.toolbarActions
import com.example.myapplication14.models.Message
import com.example.myapplication14.models.Priority
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.example.myapplication14.R.drawable as AppIcon
import com.example.myapplication14.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun AddMessageScreen(
    popUpScreen: () -> Unit,
    messageId: String,
    modifier: Modifier = Modifier,
    addMessageViewModel: AddMessageViewModel = hiltViewModel()
) {
    val message by addMessageViewModel.message

    LaunchedEffect(Unit) {
        addMessageViewModel.initialize(messageId)
    }

//    if (openDialogState.value) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        ActionToolbar(
            title = AppText.edit_message,
            modifier = Modifier.toolbarActions(),
            endActionIcon = AppIcon.ic_check,
            endAction = { addMessageViewModel.onDoneClick(popUpScreen) }
        )

        Spacer(
            modifier = Modifier.spacer()
        )

        val fieldModifier = Modifier.fieldModifier()
        BasicField(AppText.title, message.title, addMessageViewModel::onTitleChange, fieldModifier)
        BasicField(
            AppText.description,
            message.description,
            addMessageViewModel::onDescriptionChange,
            fieldModifier
        )
        BasicField(AppText.url, message.url, addMessageViewModel::onUrlChange, fieldModifier)

        Spacer(modifier = Modifier.spacer())
        CardEditors(message, addMessageViewModel::onDateChange, addMessageViewModel::onTimeChange)
        CardSelectors(
            message,
            addMessageViewModel::onPriorityChange,
            addMessageViewModel::onFlagToggle
        )

        Spacer(modifier = Modifier.spacer())

    }
}

@ExperimentalMaterialApi
@Composable
private fun CardEditors(
    message: Message,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit
) {
    val activity = LocalContext.current as AppCompatActivity

    RegularCardEditor(AppText.date, AppIcon.ic_calendar, message.dueDate, Modifier.card()) {
        showDatePicker(activity, onDateChange)
    }

    RegularCardEditor(AppText.time, AppIcon.ic_clock, message.dueTime, Modifier.card()) {
        showTimePicker(activity, onTimeChange)
    }
}

@Composable
@ExperimentalMaterialApi
private fun CardSelectors(
    message: Message,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    val prioritySelection = Priority.getByName(message.priority).name
    CardSelector(
        AppText.priority,
        Priority.getOptions(),
        prioritySelection,
        Modifier.card()
    ) { newValue ->
        onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOption.getByCheckedState(message.flag).name
    CardSelector(
        AppText.flag,
        EditFlagOption.getOptions(),
        flagSelection,
        Modifier.card()
    ) { newValue ->
        onFlagToggle(newValue)
    }
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { timeInMillis ->
            onDateChange(timeInMillis)
        }
    }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            onTimeChange(picker.hour, picker.minute)
        }
    }
}