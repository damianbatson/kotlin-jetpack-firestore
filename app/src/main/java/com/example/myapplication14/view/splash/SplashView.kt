package com.example.myapplication14.view.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication14.common.BasicButton
import com.example.myapplication14.common.ext.basicButton
import kotlinx.coroutines.delay
import com.example.myapplication14.R.string as AppText

/**
 * The authentication view which will give the user an option to choose between
 * login and register.
 */
private const val SPLASH_TIMEOUT = 1000L

@Composable
fun SplashView(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    splashmodel: SplashViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (splashmodel.showError.value) {
//            Title(title = "⚡️ FlashChat")
            Text(text = stringResource(AppText.generic_error))
            BasicButton(
                AppText.try_again,
                Modifier.basicButton()
            ) { splashmodel.onAppStart(openAndPopUp) }
        } else {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(true){
        delay(SPLASH_TIMEOUT)
        splashmodel.onAppStart(openAndPopUp)
    }
}
