package com.example.myapplication14.common.ext

import com.example.myapplication14.models.Message

fun Message?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Message?.hasDueTime(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}
