package com.features.home.screen

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.navigation.NavRoute

@Immutable
data class HomeState(val shimmer: Boolean = false): ViewState

sealed interface HomeAction: ViewAction {
    data object OpenTestPage: HomeAction
}

sealed interface HomeEffect: ViewEffect {
    data class NavigateToTestPage(val route: NavRoute): HomeEffect
}
