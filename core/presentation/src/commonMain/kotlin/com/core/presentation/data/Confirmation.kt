package com.core.presentation.data

import androidx.compose.runtime.Immutable
import com.resources.Res
import com.resources.leave_page_message
import com.resources.leave_page_title
import com.resources.stay_here
import com.resources.yes_leave
import org.jetbrains.compose.resources.StringResource

@Immutable
data class Confirmation(
    val titleRes: StringResource,
    val messageRes: StringResource,
    val negativeLabelRes: StringResource,
    val positiveLabelRes: StringResource,
) {
    companion object {
        val Default = Confirmation(
            titleRes = Res.string.leave_page_title,
            messageRes = Res.string.leave_page_message,
            negativeLabelRes = Res.string.stay_here,
            positiveLabelRes = Res.string.yes_leave
        )
    }
}