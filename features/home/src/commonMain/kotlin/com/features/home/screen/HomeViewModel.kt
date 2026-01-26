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
            HomeAction.OpenProfile -> navigateToProfile()
        }
    }

    private fun navigateToProfile() {
        postEffect(effect = HomeEffect.NavigateToProfile(route = NavRoute.Profile))
    }
}
