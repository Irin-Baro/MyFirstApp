package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel : ViewModel() {

    // Приватное состояние (изменять может только ViewModel)
    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    // Публичное состояние (для чтения в UI)
    val apps: StateFlow<List<AppInfo>> = _apps.asStateFlow()

    init {
        // Загружаем данные при создании ViewModel
        _apps.value = sampleApps
    }
}