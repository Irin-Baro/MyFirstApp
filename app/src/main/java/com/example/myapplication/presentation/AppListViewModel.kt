package com.example.myapplication.presentation

import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppRepository
import com.example.myapplication.domain.AppDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppUiModel>>(emptyList())
    val apps: StateFlow<List<AppUiModel>> = _apps.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            // Получаем данные из репозитория
            repository.getApps().collect { domainList ->
                // Маппим Domain-модели в UI-модели (подставляем иконку)
                _apps.value = domainList.map { domain ->
                    AppUiModel(
                        domain = domain,
                        icon = androidx.compose.material.icons.Icons.Filled.Home // Пример иконки
                    )
                }
            }
        }
    }
}