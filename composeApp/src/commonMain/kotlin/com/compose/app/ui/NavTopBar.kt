package com.compose.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.core.presentation.data.ScreenConfig

@Composable
fun NavTopBar(
    config: ScreenConfig,
    canGoBack: Boolean,
    onBackPressed: () -> Unit
) {
    AnimatedVisibility(visible = config.showTopBar, enter = fadeIn(), exit = fadeOut()) {
        TopAppBar(
            title = {
                Text(text = config.pageTitle)
            },
            navigationIcon = {
                if (canGoBack) {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                }
            },
            actions = {
                config.topBarActions?.invoke(this)
            }
        )
    }
}