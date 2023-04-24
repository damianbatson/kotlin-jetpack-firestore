package com.example.myapplication14.view.settings

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication14.common.*
import com.example.myapplication14.common.ext.card
import com.example.myapplication14.common.ext.spacer
import com.example.myapplication14.R.drawable as AppIcon
import com.example.myapplication14.R.string as AppText

/**
 * The login view which will help the user to authenticate themselves and go to the
 * home screen to show and send messages to others.
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsView(
//    home: () -> Unit,
//    back: () -> Unit,
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by settingsViewModel.uiState.collectAsState(initial = SettingsUiState(false))


    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BasicToolbar(AppText.settings)

        Spacer(modifier = Modifier.spacer())

        if (uiState.isAnonymousAccount) {
            RegularCardEditor(
                AppText.sign_in, AppIcon.ic_sign_in, "", Modifier.card()
            ) { settingsViewModel.onLoginClick(openScreen) }

            RegularCardEditor(
                AppText.create_account, AppIcon.ic_create_account, "", Modifier.card()
            ) { settingsViewModel.onSignUpClick(openScreen) }
        } else {
            SignOutCard { settingsViewModel.onSignOutClick(restartApp) }
            DeleteMyAccountCard { settingsViewModel.onDeleteAccountClick(restartApp) }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember {
        mutableStateOf(false)
    }
    RegularCardEditor(AppText.sign_out, AppIcon.ic_exit, "", Modifier.card()) {
        showWarningDialog = true
    }
    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    signOut(); showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }

        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember {
        mutableStateOf(false)
    }
    DangerousCardEditor(AppText.delete_my_account, AppIcon.ic_delete_account, "", Modifier.card()) {
        showWarningDialog = true
    }
    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.delete_account_title)) },
            text = { Text(stringResource(AppText.delete_account_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.delete_my_account) {
                    deleteMyAccount(); showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }

        )
    }
}

