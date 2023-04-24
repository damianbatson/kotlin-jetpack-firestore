package com.example.myapplication14.view.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication14.navigation.HOME_SCREEN
import com.example.myapplication14.navigation.SPLASH_SCREEN
import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.ConfigurationService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.view.ProjectViewModel
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    logService: LogService,
    private val authRepo: AuthService
) : ProjectViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching {
            FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener { message ->
                if (message.isSuccessful) {
                    Log.d("Firebase", "Token: " + message.result?.token)
                }
            }
            configurationService.fetchConfiguration()
        }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (authRepo.hasUser) openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackBar = false) {
            try {
                authRepo.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        }
    }
}