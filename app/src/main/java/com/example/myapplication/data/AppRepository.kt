package com.example.myapplication.data

import com.example.myapplication.domain.AppDomain
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getApps(): Flow<List<AppDomain>>

    // Новые методы для Задания 2
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}