package com.example.myapplication.data

import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.network.CatalogResponse
import com.example.myapplication.domain.AppDomain
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {

    override fun getApps(): Flow<List<AppDomain>> = flow {
        try {
            val response: List<CatalogResponse> = apiService.getCatalog()
            val domainApps = response.map { dto ->
                AppDomain(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    category = dto.category
                )
            }
            emit(domainApps)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // Последовательная загрузка (каждый запрос ждет предыдущего)
    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        for (id in ids) {
            // Вызываем метод API, внутри которого есть delay(1500)
            val rating = apiService.getRating(id)
            ratings[id] = rating
        }
        return ratings
    }

    // Параллельная загрузка (все запросы запускаются одновременно)
    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async {
                // Вызываем метод API, внутри которого есть delay(1500)
                id to apiService.getRating(id)
            }
        }.awaitAll().toMap()
    }
}