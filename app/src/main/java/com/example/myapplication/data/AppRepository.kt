package com.example.myapplication.data

import com.example.myapplication.domain.AppDomain
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getApps(): Flow<List<AppDomain>>
}