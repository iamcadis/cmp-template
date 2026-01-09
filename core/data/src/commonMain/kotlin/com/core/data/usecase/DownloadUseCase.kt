package com.core.data.usecase

import com.core.data.remote.api.ApiService
import org.koin.core.annotation.Single

@Single
class DownloadUseCase(private val api: ApiService) {
    suspend operator fun invoke(
        url: String,
        onProgress: ((received: Long, length: Long?) -> Unit)?
    ) = api.download(
        url = url,
        onProgress = onProgress
    )
}