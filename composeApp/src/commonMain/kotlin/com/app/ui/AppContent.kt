package com.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.components.ResponsiveLazyList
import com.core.ui.provider.ScreenConfig
import com.core.ui.widget.ConfirmationDialogDefault
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AppContent(screenConfig: ScreenConfig, userHasLogin: Boolean) {
    var showContent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Show Confirmation")
        }

        ResponsiveLazyList(
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            items(20) { index ->
                Card {
                    Text(
                        text = "Index ${index + 1}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    )
                }
            }
        }

        if (showContent) {
            ConfirmationDialogDefault.LeavePage(
                onCancel = {
                    showContent = false
                },
                onConfirm = {
                    showContent = false
                }
            )
        }
    }
}