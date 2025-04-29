package com.app.template.home

import com.core.viewmodel.onLoad
import com.core.network.NoContent
import com.core.network.Repository
import com.core.viewmodel.BaseViewModel

class HomeViewModel(
    private val repository: Repository
) : BaseViewModel<Home.State, Home.Action, Home.Effect>(initial = Home.State.Initial) {

    override fun onAction(action: Home.Action) {
        when(action) {
            is Home.Action.OpenDetails -> sendEffect(Home.Effect.OpenDetails(action.id))
        }
    }

    override fun fetchInitialData() {
        launchWithRetry(onRetry = ::fetchInitialData) {
            repository.get<NoContent>("")
                .onLoad { updateState { copy(loading = it) } }
                .collect { updateState { copy(id = 100) } }
        }
    }
}