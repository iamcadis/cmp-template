package com.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ui.AppContent
import com.core.data.local.LocalStorage
import com.core.ui.AppTheme
import com.core.ui.data.ScreenConfig
import com.core.ui.theme.LocalScreenProvider
import com.core.ui.theme.ScreenProvider
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

@Composable
private fun AppContainer(
    loading: Boolean,
    content: @Composable (ScreenConfig) -> Unit
) {
    var screenConfig by remember { mutableStateOf(ScreenConfig.EMPTY) }
    val screenProvider = object : ScreenProvider {
        override fun setConfig(config: ScreenConfig) {
            screenConfig = config
        }
    }

    CompositionLocalProvider(value = LocalScreenProvider provides screenProvider) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(targetState = !loading) { showLoading ->
                if (showLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    content(screenConfig)
                }
            }
        }
    }
}