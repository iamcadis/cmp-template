package com.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.core.ui.theme.AppTheme
import com.core.ui.widget.rememberAutoGridColumns
import com.core.ui.widget.rememberIsTablet

@Composable
fun ResponsiveLazyList(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    space: Dp = AppTheme.dimens.default,
    gridCols: Int = rememberAutoGridColumns(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: LazyGridScope.() -> Unit
) {
    val isTablet = rememberIsTablet()
    val columnCount = remember(isTablet, gridCols) {
        if (isTablet) gridCols else 1
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(space = space, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(space = space, alignment = Alignment.Top),
        content = content,
    )
}