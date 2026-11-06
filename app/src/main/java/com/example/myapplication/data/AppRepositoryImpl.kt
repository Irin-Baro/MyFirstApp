package com.example.myapplication.data

import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.network.CatalogResponse
import com.example.myapplication.domain.AppDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService // <-- Hilt подставит сюда Retrofit
) : AppRepository {

    override fun getApps(): Flow<List<AppDomain>> = flow {
        try {
            // Делаем запрос к серверу
            val response: List<CatalogResponse> = apiService.getCatalog()

            // Превращаем ответ сервера (DTO) в доменные модели (AppDomain)
            val domainApps = response.map { dto ->
                AppDomain(
                    id = dto.id, // Теперь это строка
                    name = dto.name,
                    description = dto.description,
                    category = dto.category
                )
            }
            // Отдаем данные в UI
            emit(domainApps)

        } catch (e: Exception) {
            // Логируем ошибку, чтобы видеть её в Logcat
            android.util.Log.e("NetworkError", "Ошибка при загрузке: ${e.message}", e)
            emit(emptyList())
        }
    }

    // Этот метод понадобится в следующем задании
    suspend fun getAppById(id: String): CatalogResponse {
        return apiService.getAppDetails(id)
    }
}