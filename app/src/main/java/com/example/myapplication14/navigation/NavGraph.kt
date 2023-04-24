package com.example.myapplication14.navigation

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication14.common.snackbar.SnackbarManager
import com.example.myapplication14.ui.theme.MyApplication14Theme
import com.example.myapplication14.view.edit_message.AddMessageScreen
import com.example.myapplication14.view.home.HomeView
import com.example.myapplication14.view.login.LoginView
import com.example.myapplication14.view.register.RegisterView
import com.example.myapplication14.view.settings.SettingsView
import com.example.myapplication14.view.splash.SplashView
import kotlinx.coroutines.CoroutineScope

/**
 * The main Navigation composable which will handle all the navigation stack.
 */

@Composable
@ExperimentalMaterialApi
fun NavGraph() {
    MyApplication14Theme {
        Surface() {
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->

                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)

                ) { navGraph(appState) }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()

) = remember(scaffoldState, navController, resources, coroutineScope) {
    NavGraphAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.navGraph(appState: NavGraphAppState) {
    composable(SPLASH_SCREEN) {
        SplashView(
            openAndPopUp = { route, popup ->
                appState.navigateAndPopUp(route, popup)
            }
        )
    }
    composable(LOGIN_SCREEN) {
        LoginView(
            openAndPopUp = { route, popup ->
                appState.navigateAndPopUp(route, popup)
            }
        )
    }
    composable(REGISTER_SCREEN) {
        RegisterView(
            openAndPopUp = { route, popup ->
                appState.navigateAndPopUp(route, popup)
            }
        )
    }

    composable(HOME_SCREEN) {
        HomeView(openScreen = { route -> appState.navigate(route) })
    }

    composable(
        route = "$ADD_MESSAGE_SCREEN$MESSAGE_ID_ARG",
        arguments = listOf(navArgument(MESSAGE_ID) { defaultValue = MESSAGE_DEFAULT_ID })
    ) {
        AddMessageScreen(
            popUpScreen = { appState.popUp() },
            messageId = it.arguments?.getString(MESSAGE_ID) ?: MESSAGE_DEFAULT_ID

        )
    }

    composable(SETTINGS_SCREEN) {
        SettingsView(
            openScreen = { route -> appState.navigate(route) },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }
}
