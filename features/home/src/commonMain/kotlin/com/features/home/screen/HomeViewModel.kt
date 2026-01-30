package com.features.home.screen

import com.core.presentation.base.BaseViewModel
import com.navigation.NavRoute
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel : BaseViewModel<HomeState, HomeAction, HomeEffect>(
    initialState = HomeState()
) {
    override fun handleAction(action: HomeAction) {
        when(action) {
            HomeAction.OpenTestPage -> navigateToTestPage()
        }
    }

    private fun navigateToTestPage() {
        postEffect(effect = HomeEffect.NavigateToTestPage(route = NavRoute.Test))
    }
}
