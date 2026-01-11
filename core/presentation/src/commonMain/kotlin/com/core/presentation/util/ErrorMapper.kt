package com.core.presentation.util

import com.core.common.error.ApiError
import com.core.common.extension.orDefault
import org.jetbrains.compose.resources.getString
import template.core.presentation.generated.resources.Res
import template.core.presentation.generated.resources.err_expired_session
import template.core.presentation.generated.resources.err_unknown

suspend fun ApiError.getTranslatedMessage() : String {
    val newMessage = mapOf(
        Pair(1, getString(Res.string.err_expired_session)),
    ).getOrElse(code.orDefault(0)) {
        getString(Res.string.err_unknown)
    }

    return if (code == null && !message.isNullOrBlank()) {
        "$message (BE)"
    } else {
        "$newMessage (BE${code})"
    }
}