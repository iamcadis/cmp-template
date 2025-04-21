package com.app.template.home

import com.core.viewmodel.ViewModel
import com.core.network.Repository

class HomeViewModel(
    private val repository: Repository
) : ViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {

    override fun createState(): HomeContract.State {
        return HomeContract.State(id = 1)
    }

    override fun fetchInitialData() {

    }
}