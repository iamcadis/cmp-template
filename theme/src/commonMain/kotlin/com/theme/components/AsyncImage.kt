package com.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.theme.DisTheme

@Composable
fun AsyncImage(
    data: Any?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = DisTheme.dimens.small),
    placeholder: Painter? = null,
    failureImage: Painter? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    CoilImage(
        imageModel = { data },
        imageOptions = ImageOptions(contentScale = contentScale, alignment = Alignment.Center),
        loading = {
            ImageDefault(painter = placeholder, contentDescription = "Fetching Image")
        },
        failure = {
            ImageDefault(painter = failureImage ?: placeholder, contentDescription = "Error Image")
        },
        modifier = Modifier
            .clip(shape = shape)
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .then(modifier)
    )
}

@Composable
@NonRestartableComposable
private fun ImageDefault(painter: Painter?, contentDescription: String) {
    painter?.let {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = it,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.4f)
            )
        }
    }
}