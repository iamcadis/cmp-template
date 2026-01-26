package com.features.auth.screen.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.core.presentation.base.BaseScreen
import com.resources.Res
import com.resources.my_profile
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileScreen() {
    BaseScreen(
        pageTitle = stringResource(Res.string.my_profile)
    ) {
        Text(text = "Coming soon")
    }
}