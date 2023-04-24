package com.example.myapplication14.services.impl

import com.example.myapplication14.models.Message
import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.HomeService
import com.example.myapplication14.services.trace
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class HomesServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val auth: AuthService

) : HomeService {

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    override val messages: Flow<List<Message>>
        get() = auth.currentUser.flatMapLatest { user ->
            currentCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
        }

    /**
     * Get the messages
     */
    override suspend fun getMessage(messageId: String): Message? =
        currentCollection(auth.currentUserId).document(messageId).get().await().toObject()

    /**
     * Send message
     */
    override suspend fun saveMessage(message: Message): String =
        trace(SAVE_TASK_TRACE) { currentCollection(auth.currentUserId).add(message).await().id }


    override suspend fun updateMessage(message: Message): Unit =
        trace(UPDATE_TASK_TRACE) {
            currentCollection(auth.currentUserId).document(message.id).set(message).await()
        }

    override suspend fun deleteMessage(messageId: String) {
        currentCollection(auth.currentUserId).document(messageId).delete().await()
    }

    override suspend fun deleteAllForUser(userId: String){
        val matchingTasks = currentCollection(userId).get().await()
        matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
    }

    private fun currentCollection(uid: String): CollectionReference = firestore.collection(
        USER_COLLECTION
    ).document(uid).collection(MESSAGE_COLLECTION)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val MESSAGE_COLLECTION = "items"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}