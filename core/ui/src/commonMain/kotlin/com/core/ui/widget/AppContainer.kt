package com.core.ui.widget

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
import com.core.ui.provider.LocalScreenProvider
import com.core.ui.provider.ScreenConfig
import com.core.ui.provider.ScreenProvider


@Composable
fun AppContainer(
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