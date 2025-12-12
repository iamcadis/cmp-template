package com.core.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.core.ui.components.ResponsiveGroup
import com.core.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import template.core.ui.generated.resources.Res
import template.core.ui.generated.resources.leave_page_message
import template.core.ui.generated.resources.leave_page_title
import template.core.ui.generated.resources.stay_here
import template.core.ui.generated.resources.yes_leave

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    actions: @Composable (modifier: Modifier) -> Unit,
    onDismiss: () -> Unit = {},
    dialogProperties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    actionsLayoutDirectionTablet: LayoutDirection = LayoutDirection.Rtl
) {
    val isTablet = rememberIsTablet()
    val maxDialogWidth = if (isTablet) 550.dp else Dp.Unspecified

    Dialog(onDismissRequest = onDismiss, properties = dialogProperties) {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.widthIn(max = maxDialogWidth)
                .padding(all = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = message,
                    color = AppTheme.colors.onSurfaceVariant,
                )
                Spacer(Modifier.height(16.dp))
                ResponsiveGroup(rowGap = 24.dp, layoutDirection = actionsLayoutDirectionTablet) {
                    actions(it)
                }
            }
        }
    }
}

object ConfirmationDialogDefault {

    @Composable
    fun LeavePage(onCancel: () -> Unit, onConfirm: () -> Unit) {
        ConfirmationDialog(
            title = stringResource(Res.string.leave_page_title),
            message = stringResource(Res.string.leave_page_message),
            actions = { modifier ->
                Button(
                    onClick = onConfirm,
                    modifier = modifier
                ) {
                    Text(text = stringResource(Res.string.yes_leave))
                }
                OutlinedButton(
                    onClick = onCancel,
                    modifier = modifier
                ) {
                    Text(text = stringResource(Res.string.stay_here))
                }
            }
        )
    }
}