package com.core.presentation.base

interface ViewAction {
    interface CallApi: ViewAction
    interface SideEffect: ViewAction
    interface UpdateData: ViewAction
}

interface ViewEffect

interface ViewState