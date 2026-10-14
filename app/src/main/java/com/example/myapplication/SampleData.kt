package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

// Временный класс: пока используем иконки из библиотеки Material.
// Позже, если нужно, заменим их на реальные иконки приложений.
data class AppInfo(
    val icon: ImageVector,
    val name: String,
    val description: String,
    val category: String
)

val sampleApps = listOf(
    AppInfo(
        icon = Icons.Filled.Home,
        name = "Дом",
        description = "Управление умным домом",
        category = "Дом"
    ),
    AppInfo(
        icon = Icons.Filled.Email,
        name = "Почта",
        description = "Электронная почта для работы",
        category = "Работа"
    ),
    AppInfo(
        icon = Icons.Filled.Call,
        name = "Телефон",
        description = "Звонки и контакты",
        category = "Связь"
    ),
    AppInfo(
        icon = Icons.Filled.DateRange,
        name = "Календарь",
        description = "Планирование встреч",
        category = "Работа"
    ),
    AppInfo(
        icon = Icons.Filled.ShoppingCart,
        name = "Магазин",
        description = "Покупки онлайн",
        category = "Покупки"
    ),
    AppInfo(
        icon = Icons.Filled.PlayArrow,
        name = "Видео",
        description = "Просмотр фильмов и сериалов",
        category = "Развлечения"
    ),
    AppInfo(
        icon = Icons.Filled.Star,
        name = "Избранное",
        description = "Важные заметки и закладки",
        category = "Личное"
    ),
    AppInfo(
        icon = Icons.Filled.Search,
        name = "Поиск",
        description = "Поиск по интернету",
        category = "Инструменты"
    ),
    AppInfo(
        icon = Icons.Filled.Settings,
        name = "Настройки",
        description = "Параметры системы",
        category = "Система"
    ),
    AppInfo(
        icon = Icons.Filled.Face,
        name = "Профиль",
        description = "Личный кабинет пользователя",
        category = "Личное"
    )
)