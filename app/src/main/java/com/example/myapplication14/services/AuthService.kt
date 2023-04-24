package com.example.myapplication14.services

import com.example.myapplication14.models.User
import kotlinx.coroutines.flow.Flow

interface AuthService {

    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}