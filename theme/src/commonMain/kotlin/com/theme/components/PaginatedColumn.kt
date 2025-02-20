package com.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.theme.DisTheme

@Composable
fun PaginatedColumn(
    size: Int,
    perPage: Int,
    loading: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    indicatorLoading: @Composable () -> Unit = { PaginatedLazyColumnDefaults.IndicatorLoading() },
    content: LazyListScope.() -> Unit
) {

    val isLastItemVisible by remember {
        derivedStateOf {
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            lastVisibleIndex == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(isLastItemVisible) {
        if (isLastItemVisible && size % perPage == 0 && !loading) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        content()
        if (loading) {
            item { indicatorLoading() }
        }
    }
}

@Immutable
object PaginatedLazyColumnDefaults {
    @Composable
    fun IndicatorLoading() {
        Box(
            modifier = Modifier.fillMaxWidth().padding(all = DisTheme.dimens.default),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}