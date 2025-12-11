package com.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.ui.AppContent
import com.core.data.local.LocalStorage
import com.core.ui.widget.AppContainer
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

private const val LOADING_TIME = 700

@Composable
fun App() {
    val storage = koinInject<LocalStorage>()
    val hasLogin by storage.userHasLogin.collectAsStateWithLifecycle(initialValue = false)
    var showLoading by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(true) {
        delay(LOADING_TIME.toLong())
        showLoading = false
    }

    MaterialTheme {
        AppContainer(showLoading = showLoading, loadingTime = LOADING_TIME) { config ->
            AppContent(config = config, userHasLogin = hasLogin)
        }
    }
}