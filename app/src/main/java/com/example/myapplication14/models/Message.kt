package com.example.myapplication14.models

import com.google.firebase.firestore.DocumentId

data class Message(
    @DocumentId val id: String = "",
    val description: String = "",
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val url: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val userId: String = ""
)



