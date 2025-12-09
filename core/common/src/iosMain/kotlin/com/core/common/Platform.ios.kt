package com.core.common

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
actual object Platform {
    actual val isDebug: Boolean
        get() = Platform.isDebugBinary
}