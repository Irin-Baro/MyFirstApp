package com.example.myapplication.domain

import com.example.myapplication.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<List<AppDomain>> {
        return repository.getApps()
    }
}