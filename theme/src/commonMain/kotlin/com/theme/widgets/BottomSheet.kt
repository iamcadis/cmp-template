package com.theme.widgets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * A simple example of a modal bottom sheet looks like this:
 * @param modifier Optional [Modifier] for the bottom sheet.
 * @param dismissible The state of the bottom sheet can dismiss on drag or touch outside.
 * @param shape The shape of the bottom sheet.
 * @param onDismissRequest Executes when the user clicks outside of the bottom sheet, after sheet
 * animates to [Hidden].
 * @param content The content to be displayed inside the bottom sheet.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    dismissible: Boolean = true,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { if (!dismissible) it != Hidden else true }
    )

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        shape = shape,
        content = content
    )
}