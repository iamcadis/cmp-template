package com.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.core.ui.component.CustomSnackbarHostState
import com.core.ui.component.LocalSnackbarHostState
import com.core.ui.util.LocalScaffoldState
import com.core.ui.util.ScaffoldState

@Composable
fun NavigationHost(
    authenticated: Boolean,
    onBackPressed: () -> Unit
) {
    val scaffoldState = remember { ScaffoldState() }
    val snackbarHostState = remember { CustomSnackbarHostState() }

    CompositionLocalProvider(
        LocalScaffoldState provides scaffoldState,
        LocalSnackbarHostState provides snackbarHostState
    ) {

    }
}