package com.example.myapplication14.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication14.common.ActionToolbar
import com.example.myapplication14.common.ext.smallSpacer
import com.example.myapplication14.common.ext.toolbarActions
import com.example.myapplication14.R.drawable as AppIcon
import com.example.myapplication14.R.string as AppText

/**
 * The home view which will contain all the code related to the view for HOME.
 *
 * Here we will show the list of chat messages sent by user.
 * And also give an option to send a message and logout.
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun HomeView(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeViewModel.onAddClick(openScreen)
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(
                    Icons.Filled.Add, "Add"
                )
            }
        }
    ) {
        val messages = homeViewModel.messages.collectAsStateWithLifecycle(emptyList())
        val hasEditIcon by homeViewModel.hasEditIcon
        val options by homeViewModel.options

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolbar(
                title = AppText.messages,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { homeViewModel.onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 0.85f, fill = true),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                reverseLayout = true
            ) {
                items(messages.value, key = { it.id }) { messageItem ->

                    MessageItem(
                        message = messageItem,
                        options = options,
                        hasEditIcon = hasEditIcon,
                        onCheckChange = { homeViewModel.onTaskCheckChange(messageItem) },
                        onActionClick = { action ->
                            homeViewModel.onTaskActionClick(openScreen, messageItem, action)
                        },
                        onEditClick = { homeViewModel.onMessageEditClick(openScreen, messageItem) }
                    )
                }
            }
        }
    }
    LaunchedEffect(homeViewModel) {
        homeViewModel.loadTaskOptions()
    }
}

