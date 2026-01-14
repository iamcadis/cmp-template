package com.compose.app

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveable
import com.compose.app.ui.NavHost
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState

@Composable
fun App() {
    val scaffoldState = rememberSaveable(saver = ScaffoldState.Saver) {
        ScaffoldState()
    }

    AppTheme {
        Surface {
            CompositionLocalProvider(value = LocalScaffoldState provides scaffoldState) {
                NavHost(scaffoldState = scaffoldState)
            }
        }
    }
}