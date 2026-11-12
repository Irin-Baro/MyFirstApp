package com.example.myapplication.presentation

import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppUiModel>>(emptyList())
    val apps: StateFlow<List<AppUiModel>> = _apps.asStateFlow()

    // Переменные для вывода времени в UI (можно вывести через Toast или Text)
    var sequentialTime: Long = 0
        private set
    var parallelTime: Long = 0
        private set

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            repository.getApps().collect { domainList ->
                // Маппим Domain в UI-модели (рейтинг пока null)
                val uiModels = domainList.map { domain ->
                    AppUiModel(
                        domain = domain,
                        icon = androidx.compose.material.icons.Icons.Filled.Home,
                        rating = null
                    )
                }
                _apps.value = uiModels

                // Сначала параллельно (быстро)
                loadRatingsInParallel()

                // Потом последовательно (медленно) — для замера времени
                loadRatingsSequentially()
            }
        }
    }

    // Загрузка рейтингов последовательно
    fun loadRatingsSequentially() {
        val ids = _apps.value.map { it.domain.id }
        viewModelScope.launch {
            val time = measureTimeMillis {
                val ratings = repository.loadRatingsSequentially(ids)
                updateRatings(ratings)
            }
            sequentialTime = time
            println(">>> Последовательно: $time мс")
        }
    }

    // Загрузка рейтингов параллельно
    fun loadRatingsInParallel() {
        val ids = _apps.value.map { it.domain.id }
        viewModelScope.launch {
            val time = measureTimeMillis {
                val ratings = repository.loadRatingsInParallel(ids)
                updateRatings(ratings)
            }
            parallelTime = time
            println(">>> Параллельно: $time мс")
        }
    }

    private fun updateRatings(ratings: Map<String, Float>) {
        _apps.value = _apps.value.map { uiModel ->
            val rating = ratings[uiModel.domain.id]
            uiModel.copy(rating = rating)
        }
    }
}