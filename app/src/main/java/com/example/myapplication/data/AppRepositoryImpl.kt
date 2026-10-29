package com.example.myapplication.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.myapplication.domain.AppDomain
import com.example.myapplication.domain.AppRepository

class AppRepositoryImpl : AppRepository {

    private val mockData = listOf(
        AppDto(1, Icons.Filled.Home, "Дом", "Управление умным домом", "Дом"),
        AppDto(2, Icons.Filled.Email, "Почта", "Электронная почта для работы", "Работа"),
        AppDto(3, Icons.Filled.Call, "Телефон", "Звонки и контакты", "Связь"),
        AppDto(4, Icons.Filled.DateRange, "Календарь", "Планирование встреч", "Работа"),
        AppDto(5, Icons.Filled.ShoppingCart, "Магазин", "Покупки онлайн", "Покупки"),
        AppDto(6, Icons.Filled.PlayArrow, "Видео", "Просмотр фильмов и сериалов", "Развлечения"),
        AppDto(7, Icons.Filled.Star, "Избранное", "Важные заметки и закладки", "Личное"),
        AppDto(8, Icons.Filled.Search, "Поиск", "Поиск по интернету", "Инструменты"),
        AppDto(9, Icons.Filled.Settings, "Настройки", "Параметры системы", "Система"),
        AppDto(10, Icons.Filled.Face, "Профиль", "Личный кабинет пользователя", "Личное")
    )

    override fun getApps(): List<AppDomain> {
        // Возвращаем данные, конвертируя их через Mapper
        return mockData.map { it.toDomain() }
    }
}