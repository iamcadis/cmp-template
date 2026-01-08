package com.core.common

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual val isDebuggingMode: Boolean
    get() = Platform.isDebugBinary