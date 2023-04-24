package com.example.myapplication14.view.login

import androidx.compose.runtime.mutableStateOf
import com.example.myapplication14.common.ext.isValidEmail
import com.example.myapplication14.common.snackbar.SnackbarManager
import com.example.myapplication14.navigation.HOME_SCREEN
import com.example.myapplication14.navigation.LOGIN_SCREEN
import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.view.ProjectViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.myapplication14.R.string as AppText

/**
 * View model for the login view.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    logService: LogService,
    private val authService: AuthService
) : ProjectViewModel(logService) {
    private val auth: FirebaseAuth = Firebase.auth
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    // Update email
    fun onEmailChange(newEmail: String) {
        uiState.value = uiState.value.copy(email = newEmail)
    }

    // Update password
    fun onPasswordChange(newPassword: String) {
        uiState.value = uiState.value.copy(password = newPassword)
    }

    // Register user
    fun loginUser(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {

            authService.signInWithEmailAndPassword(email, password)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
        }
    }

}