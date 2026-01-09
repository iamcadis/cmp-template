package com.compose.app.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState

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
        Text("TESTING")
    }
}