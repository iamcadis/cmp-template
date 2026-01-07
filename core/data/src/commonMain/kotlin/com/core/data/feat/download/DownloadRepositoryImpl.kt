package com.core.data.feat.download

import com.core.data.remote.api.ApiService
import com.core.domain.feat.download.DownloadRepository
import org.koin.core.annotation.Single

@Single(binds = [DownloadRepository::class])
internal class DownloadRepositoryImpl(private val apiService: ApiService) : DownloadRepository {
    override suspend fun downloadFile(
        url: String,
        onProgress: ((received: Long, length: Long?) -> Unit)?
    ): ByteArray {
        return apiService.download(url = url, onProgress = onProgress)
    }
}