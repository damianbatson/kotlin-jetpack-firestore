package com.example.myapplication14.services

import com.example.myapplication14.models.Message
import kotlinx.coroutines.flow.Flow

interface HomeService {

    val messages: Flow<List<Message>>

    suspend fun getMessage(messageId: String): Message?

    suspend fun saveMessage(message: Message): String
    suspend fun updateMessage(message: Message)
    suspend fun deleteMessage(messageId: String)
    suspend fun deleteAllForUser(userId: String)

}