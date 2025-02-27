package com.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers
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

        val response = httpClient.request(url) {
            this.method = method
            this.contentType(contentType)

            if (method != HttpMethod.Get && data != null) {
                setBody(data)
            }

            if (!needAuth) {
                headers {
                    remove(HttpHeaders.Authorization)
                }
            }
        }

        emit(response.takeIf { it.status == HttpStatusCode.OK }?.body() ?: NoContent as T)

    }.flowOn(Dispatchers.IO)
}