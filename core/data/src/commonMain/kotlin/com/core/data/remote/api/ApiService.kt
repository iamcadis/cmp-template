package com.core.data.remote.api

import com.core.common.DispatcherProvider
import com.core.common.error.NoInternetException
import com.core.data.remote.ConnectivityObserver
import com.core.data.remote.utils.NoContent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.withContext

class ApiService(
    val httpClient: HttpClient,
    val dispatcher: DispatcherProvider,
    val connectivityObserver: ConnectivityObserver
) {

    suspend inline fun <reified T> get(url: String) = request<T>(
        url = url,
        method = HttpMethod.Get
    )

    suspend inline fun <reified T> post(url: String, data: Any?) = request<T>(
        url = url,
        method = HttpMethod.Post,
        data = data
    )

    suspend inline fun <reified T> request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        contentType: ContentType = ContentType.Application.Json
    ) = withContext(dispatcher.io) {

        if (!connectivityObserver.isConnected()) {
            throw NoInternetException()
        }

        val response = httpClient.request(url) {
            this.method = method
            this.setBody(data)
            this.contentType(contentType)
        }

        if (response.status == HttpStatusCode.NoContent) {
            NoContent as T
        } else {
            response.body<T>()
        }
    }
}