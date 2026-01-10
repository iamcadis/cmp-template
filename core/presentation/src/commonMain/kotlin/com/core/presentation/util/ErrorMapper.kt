package com.core.presentation.util

import com.core.common.error.ApiError
import org.jetbrains.compose.resources.getString
import template.core.presentation.generated.resources.Res
import template.core.presentation.generated.resources.err_expired_session
import template.core.presentation.generated.resources.err_unknown

suspend fun ApiError.getTranslatedMessage() = mapOf(
    Pair(code, getString(Res.string.err_expired_session)),
).getOrElse(code) {
    getString(Res.string.err_unknown)
}