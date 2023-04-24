package com.example.myapplication14.view.settings

import com.example.myapplication14.navigation.LOGIN_SCREEN
import com.example.myapplication14.navigation.REGISTER_SCREEN
import com.example.myapplication14.navigation.SPLASH_SCREEN
import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.HomeService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.view.ProjectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * View model for the login view.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val authService: AuthService,
    private val homeService: HomeService,
) : ProjectViewModel(logService) {
    val uiState = authService.currentUser.map { SettingsUiState(it.isAnonymous) }

    // Update email
    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(REGISTER_SCREEN)

    // Update password
    fun onSignOutClick(restartApp: (String) -> Unit) {

        launchCatching {
            authService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    // Register user
    fun onDeleteAccountClick(restartApp: (String) -> Unit) {

        launchCatching {
            homeService.deleteAllForUser(authService.currentUserId)
            authService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }
}