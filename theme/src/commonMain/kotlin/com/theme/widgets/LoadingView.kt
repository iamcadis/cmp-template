package com.theme.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.theme.DisTheme

@Composable
fun LoadingView(
    isLoading: Boolean,
    spinnerSize: Dp = DisTheme.dimens.superLarge,
    backgroundColor: Color = Color.Black.copy(alpha = 0.3f),
) {
    AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .clickable(enabled = true, onClick = {})
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(spinnerSize)
                    .align(Alignment.Center)
            )
        }
    }
}
