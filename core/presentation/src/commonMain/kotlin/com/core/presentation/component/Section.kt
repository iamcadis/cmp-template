//package com.core.presentation.component
//
//import androidx.annotation.FloatRange
//import androidx.annotation.IntRange
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.ListItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.key
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.font.FontWeight
//import com.core.presentation.theme.AppTheme
//import kotlinx.collections.immutable.PersistentList
//
//@Composable
//fun <T> Section(
//    title: String,
//    items: PersistentList<T>,
//    modifier: Modifier = Modifier,
//    caption: String? = null,
//    content: @Composable ColumnScope.(T) -> Unit
//) {
//    Column(modifier = modifier) {
//        Text(
//            text = title,
//            style = AppTheme.typography.bodyMedium,
//            color = AppTheme.colors.onSurfaceVariant,
//            fontWeight = FontWeight.Medium
//        )
//        caption?.let {
//            Text(
//                text = it,
//                style = AppTheme.typography.bodySmall,
//                color = AppTheme.colors.onSurfaceVariant,
//                modifier = Modifier
//                    .padding(top = AppTheme.dimens.extraSmall)
//            )
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(size = AppTheme.dimens.default))
//                .background(color = AppTheme.colors.surfaceContainerHighest)
//        ) {
//            items.forEach { item ->
//                key(item.hashCode()) {
//                    ListItem()
//                }
//            }
//        }
//    }
//}