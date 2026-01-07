package com.core.domain.repository

interface DownloadRepository {
    suspend fun downloadFile(
        url: String,
        onProgress: ((received: Long, length: Long?) -> Unit)?
    ) : ByteArray
}