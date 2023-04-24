package com.example.myapplication14.view.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication14.common.*
import com.example.myapplication14.common.ext.basicButton
import com.example.myapplication14.common.ext.fieldModifier
import com.example.myapplication14.R.string as AppText

/**
 * The Register view which will be helpful for the user to register themselves into
 * our database and go to the home screen to see and send messages.
 */

@Composable
fun RegisterView(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by registerViewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

    BasicToolbar(
        AppText.create_account
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        EmailField(
            uiState.email,
            registerViewModel::onEmailChange,
            fieldModifier
        )
        PasswordField(
            uiState.password,
            registerViewModel::onPasswordChange,
            fieldModifier
        )
        RepeatPasswordField(
            uiState.repeatPassword,
            registerViewModel::onRepeatPasswordChange,
            fieldModifier
        )
        Spacer(modifier = Modifier.height(20.dp))

        BasicButton(
            AppText.create_account,
            Modifier.basicButton()
        ) {
            registerViewModel.registerUser(openAndPopUp)
        }
    }
}

