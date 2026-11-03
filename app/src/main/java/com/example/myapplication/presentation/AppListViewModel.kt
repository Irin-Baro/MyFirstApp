package com.example.myapplication.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.AppRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel : ViewModel() {

    private val repository = AppRepositoryImpl()

    // Теперь ViewModel хранит список UI-моделей (данные + иконка)
    private val _apps = MutableStateFlow<List<AppUiModel>>(emptyList())
    val apps: StateFlow<List<AppUiModel>> = _apps.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        val domainApps = repository.getApps()

        // Сопоставляем иконки по индексу (или по ID)
        val iconsList = listOf(
            Icons.Filled.Home,
            Icons.Filled.Email,
            Icons.Filled.Call,
            Icons.Filled.DateRange,
            Icons.Filled.ShoppingCart,
            Icons.Filled.PlayArrow,
            Icons.Filled.Star,
            Icons.Filled.Search,
            Icons.Filled.Settings,
            Icons.Filled.Face
        )

        // Превращаем Domain-модели в UI-модели, добавляя иконку
        _apps.value = domainApps.mapIndexed { index, app ->
            val icon = iconsList.getOrElse(index) { Icons.Filled.Info }
            AppUiModel(domain = app, icon = icon)
        }
    }
}