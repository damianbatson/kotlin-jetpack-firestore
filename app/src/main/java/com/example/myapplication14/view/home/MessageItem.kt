package com.example.myapplication14.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication14.common.DropdownContextMenu
import com.example.myapplication14.common.ext.contextMenu
import com.example.myapplication14.common.ext.hasDueDate
import com.example.myapplication14.common.ext.hasDueTime
import com.example.myapplication14.models.Message
import com.example.myapplication14.ui.theme.Purple200
import com.example.myapplication14.ui.theme.Teal200
import com.example.myapplication14.R.drawable as AppIcon

@Composable
@ExperimentalMaterialApi
fun MessageItem(
    message: Message,
    options: List<String>,
    hasEditIcon: Boolean,
    onCheckChange: () -> Unit,
    onActionClick: (String) -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = message.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp, 0.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = message.title, style = MaterialTheme.typography.subtitle2)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = getDueDateAndTime(message), fontSize = 12.sp)
                }
            }

            if (message.flag) {
                Icon(
                    painter = painterResource(AppIcon.ic_flag),
                    tint = Teal200,
                    contentDescription = "Flag"
                )
            }

            if (hasEditIcon) {
                IconButton(onClick = { onEditClick() }) {
                    Icon(
                        painter = painterResource(AppIcon.ic_edit),
                        tint = Purple200,
                        contentDescription = "Flag"
                    )
                }
            }

            DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
        }
    }
}

private fun getDueDateAndTime(message: Message): String {
    val stringBuilder = StringBuilder("")

    if (message.hasDueDate()) {
        stringBuilder.append(message.dueDate)
        stringBuilder.append(" ")
    }

    if (message.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(message.dueTime)
    }

    return stringBuilder.toString()
}
