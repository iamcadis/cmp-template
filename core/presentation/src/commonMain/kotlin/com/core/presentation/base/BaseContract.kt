package com.core.presentation.base

interface ViewAction

interface ViewEffect

interface ViewState {
    val pageLoading: Boolean
        get() = false
}