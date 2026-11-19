package com.example.myapplication.presentation

import com.example.myapplication.domain.AppDomain

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState
    data object Installed : InstallState
}

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data class Error(val message: String) : AppDetailsState
    data class Content(
        val appDetails: AppDomain,
        val installState: InstallState = InstallState.NotInstalled
    ) : AppDetailsState
}