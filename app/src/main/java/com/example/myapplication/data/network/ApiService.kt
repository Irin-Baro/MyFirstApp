package com.example.myapplication.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Запрос каталога приложений (эндпоинт /catalog)
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogResponse>

    // Запрос информации о конкретном приложении по ID (эндпоинт /{id})
    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): CatalogResponse
}