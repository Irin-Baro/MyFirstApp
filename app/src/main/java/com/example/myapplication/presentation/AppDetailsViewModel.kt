package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppRepository
import com.example.myapplication.domain.AppDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var installJob: Job? = null

    // Инициализация состояния, когда UI передает данные приложения
    fun initContent(app: AppDomain) {
        _state.value = AppDetailsState.Content(
            appDetails = app,
            installState = InstallState.NotInstalled
        )
    }

    fun onInstallClick() {
        val current = _state.value
        if (current !is AppDetailsState.Content) return

        if (current.installState is InstallState.Installing) {
            // Отменяем установку
            installJob?.cancel()
            installJob = null
            updateInstallState(InstallState.NotInstalled)
        } else {
            // Запускаем установку
            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private fun updateInstallState(newState: InstallState) {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(installState = newState)
            } else {
                currentState
            }
        }
    }

    private suspend fun emulateInstall() {
        for (progress in 0..100 step 5) {
            updateInstallState(InstallState.Installing(progress))
            delay(150)
        }
        updateInstallState(InstallState.Installed)
    }
}