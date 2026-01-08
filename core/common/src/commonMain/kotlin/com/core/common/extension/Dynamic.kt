package com.core.common.extension

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}

fun <T> T?.ifNull(action: () -> Unit): T? {
    if (this == null) action()
    return this
}

fun <T> T?.ifNotNull(action: (T) -> Unit): T? {
    if (this != null) action(this)
    return this
}