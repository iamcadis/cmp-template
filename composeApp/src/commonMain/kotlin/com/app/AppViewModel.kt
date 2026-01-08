package com.app

import androidx.lifecycle.viewModelScope
import com.core.data.local.LocalStorage
import com.core.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    storage: LocalStorage
) : BaseViewModel<AppContract.State, AppContract.Action, AppContract.Effect>(
    initialState = AppContract.State()
) {
    val userId = storage.userId
        .mapNotNull { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}