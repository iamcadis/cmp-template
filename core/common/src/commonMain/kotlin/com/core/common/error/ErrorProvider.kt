package com.core.common.error

interface ErrorProvider {
    fun getMessage(error: Throwable): String?
}