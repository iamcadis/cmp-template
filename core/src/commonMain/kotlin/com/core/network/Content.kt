package com.core.network

import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMessageBuilder
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
object NoContent : OutgoingContent.NoContent()

fun HttpMessageBuilder.addTokenIf(condition: Boolean, token: String?) {
    if (condition && token.isNullOrBlank().not()) {
        header(HttpHeaders.Authorization, "Bearer $token")
    }
}

fun HttpRequestBuilder.addDataBodyAs(data: Any?, type: ContentType) {
    data?.let {
        this.setBody(data)
        this.contentType(type)
    }
}

suspend inline fun <reified T> HttpResponse.parseResponse(): T {
    return when(status) {
        HttpStatusCode.NoContent -> NoContent as T
        HttpStatusCode.OK -> {
            when(contentType()?.match(ContentType.Application.Json) == true) {
                true -> body<T>()
                false -> NoContent as T
            }
        }
        else -> throw ResponseException(
            response = this,
            cachedResponseText = "Error: ${status}, Body: ${body<String>()}"
        )
    }
}