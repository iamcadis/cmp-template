@file:Suppress("unused")

package com.core.ui.base

interface ViewAction

interface ViewEffect

interface ViewState {
    val pageLoading: Boolean
        get() = false
}

object EmptyAction : ViewAction

object EmptyEffect : ViewEffect