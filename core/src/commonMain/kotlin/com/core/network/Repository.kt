package com.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(val httpClient: HttpClient) {
    inline fun <reified T> get(url: String, needAuth: Boolean = true): Flow<T> {
        return request(url = url, method = HttpMethod.Get, needAuth = needAuth)
    }

    inline fun <reified T> post(url: String, data: Any?, needAuth: Boolean = true): Flow<T> {
        return request(url, method = HttpMethod.Post, data = data, needAuth = needAuth)
    }

    inline fun <reified T> put(url: String, data: Any?, needAuth: Boolean = true): Flow<T> {
        return request(url, method = HttpMethod.Put, data = data, needAuth = needAuth)
    }

    inline fun <reified T> request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        needAuth: Boolean = true,
        contentType: ContentType = ContentType.Application.Json
    ) = flow {
        httpClient.request(url) {
            this.method = method
            this.addTokenIf(condition = needAuth, token = "testing")
            this.addDataBodyAs(data = data, type = contentType)
        }.let {
            emit(it.parseResponse<T>())
        }
    }.flowOn(Dispatchers.IO)
}