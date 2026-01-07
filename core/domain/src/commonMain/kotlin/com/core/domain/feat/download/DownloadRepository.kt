package com.core.domain.feat.download

interface DownloadRepository {
    suspend fun downloadFile(
        url: String,
        onProgress: ((received: Long, length: Long?) -> Unit)?
    ) : ByteArray
}