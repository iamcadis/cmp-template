package com.compose.app.ui

import androidx.compose.runtime.Composable

@Composable
expect fun NavBack(canGoBack: () -> Boolean, onNavigateBack: () -> Unit)