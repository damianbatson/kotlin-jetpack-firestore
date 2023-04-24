package com.example.myapplication14.view.register

import androidx.compose.runtime.mutableStateOf
import com.example.myapplication14.common.ext.isValidEmail
import com.example.myapplication14.common.ext.isValidPassword
import com.example.myapplication14.common.ext.passwordMatches
import com.example.myapplication14.common.snackbar.SnackbarManager
import com.example.myapplication14.navigation.HOME_SCREEN
import com.example.myapplication14.navigation.LOGIN_SCREEN
import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.view.ProjectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.myapplication14.R.string as AppText

/**
 * View model for the login view.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val authService: AuthService
) : ProjectViewModel(logService) {
    var uiState = mutableStateOf(RegisterUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    // Update email
    fun onEmailChange(newEmail: String) {
        uiState.value = uiState.value.copy(email = newEmail)
    }

    // Update password
    fun onPasswordChange(newPassword: String) {
        uiState.value = uiState.value.copy(password = newPassword)
    }

    fun onRepeatPasswordChange(newValue: String){
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    // Register user
    fun registerUser(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }
        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }
        if(!password.passwordMatches(uiState.value.repeatPassword)){
            SnackbarManager.showMessage(AppText.password_match_error)
        }

        launchCatching {

            authService.linkAccount(email, password)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)

        }
    }
}
