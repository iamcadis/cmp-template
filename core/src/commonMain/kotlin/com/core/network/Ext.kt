package com.core.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

fun HttpRequestBuilder.addDataBody(type: ContentType, data: Any?) {
    this.contentType(type)

    if (method != HttpMethod.Get && data != null) {
        this.setBody(data)
    }
}

fun HttpRequestBuilder.removeAuthorizationIf(condition: Boolean) {
    if (condition) {
        this.headers { remove(HttpHeaders.Authorization) }
    }
}

val HttpResponse.isJson: Boolean
    get() = headers[HttpHeaders.ContentType]?.contains("application/json", true) == true