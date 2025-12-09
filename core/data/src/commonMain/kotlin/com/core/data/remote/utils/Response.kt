package com.core.data.remote.utils

import io.ktor.http.content.OutgoingContent
import kotlinx.serialization.Serializable

@Serializable
data object NoContent : OutgoingContent.NoContent() {
    override val contentLength: Long = 0
}