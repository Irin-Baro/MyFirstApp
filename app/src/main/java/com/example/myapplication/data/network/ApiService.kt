package com.example.myapplication.data.network

import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogResponse>

    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): CatalogResponse

    // Метод для получения рейтинга (сэмулирован)
    suspend fun getRating(id: String): Float {
        delay(1500) // Эмуляция сетевой задержки
        return (30..50).random() / 10f
    }
}