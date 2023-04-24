package com.example.myapplication14.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication14.common.BasicButton
import com.example.myapplication14.common.BasicToolbar
import com.example.myapplication14.common.EmailField
import com.example.myapplication14.common.PasswordField
import com.example.myapplication14.common.ext.basicButton
import com.example.myapplication14.common.ext.fieldModifier
import com.example.myapplication14.R.string as AppText

/**
 * The login view which will help the user to authenticate themselves and go to the
 * home screen to show and send messages to others.
 */

@Composable
fun LoginView(
//    home: () -> Unit,
//    back: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState

    BasicToolbar(AppText.login_details)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        EmailField(
            value = uiState.email,
            loginViewModel::onEmailChange,
            Modifier.fieldModifier()
        )
        PasswordField(
            value = uiState.password,
            loginViewModel::onPasswordChange,
            Modifier.fieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))

        BasicButton(
            AppText.sign_in,
            Modifier.basicButton()
        ) { loginViewModel.loginUser(openAndPopUp) }

    }
}

