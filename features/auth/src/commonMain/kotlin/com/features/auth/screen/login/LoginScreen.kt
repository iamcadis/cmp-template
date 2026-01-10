package com.features.auth.screen.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.core.presentation.base.BaseScreen

@Composable
internal fun LoginScreen() {
    BaseScreen(
        showTopBar = false
    ) {
        Text("LOGIN PAGE")
    }
}