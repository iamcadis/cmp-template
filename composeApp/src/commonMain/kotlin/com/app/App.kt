package com.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ui.AppContent
import com.core.data.local.LocalStorage
import com.core.ui.theme.AppTheme
import com.core.ui.widget.AppContainer
import org.koin.compose.koinInject

@Composable
fun App() {
    val storage = koinInject<LocalStorage>()
    val hasLogin by storage.userHasLogin.collectAsStateWithLifecycle(initialValue = false)

    AppTheme {
        AppContainer(loading = !hasLogin) { screenConfig ->
            AppContent(screenConfig = screenConfig, userHasLogin = hasLogin)
        }
    }
}