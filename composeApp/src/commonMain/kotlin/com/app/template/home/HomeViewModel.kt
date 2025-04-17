package com.app.template.home

import com.core.network.NoContent
import com.core.network.Repository
import com.core.mvi.ViewModel

class HomeViewModel(
    private val repository: Repository
) : ViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {

    override fun initializeState(): HomeContract.State {
        return HomeContract.State(loading = false)
    }

    override suspend fun loadInitialData() {
        repository.get<NoContent>(url = "")
    }
}

