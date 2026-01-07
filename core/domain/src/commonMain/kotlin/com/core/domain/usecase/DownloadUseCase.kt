package com.core.domain.usecase

import com.core.domain.repository.DownloadRepository
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