package com.core.domain.feat.download

import org.koin.core.annotation.Single

@Single
class DownloadUseCase(private val downloadRepository: DownloadRepository) {
    suspend operator fun invoke(
        url: String,
        onProgress: ((received: Long, length: Long?) -> Unit)?
    ) = downloadRepository.downloadFile(
        url = url,
        onProgress = onProgress
    )
}