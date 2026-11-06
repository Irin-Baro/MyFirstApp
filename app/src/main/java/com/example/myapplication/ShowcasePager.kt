package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.AppDomain

@Composable
fun ShowcasePager(
    onAppClick: (AppDomain) -> Unit
) {
    // Берем захардкоженные данные, чтобы не зависеть от репозитория
    // (в рамках задания по DI это допустимая заглушка)
    val showcaseApps = listOf(
        AppDomain(id = "1", name = "Дом", description = "Управление", category = "Дом"),
        AppDomain(id = "2", name = "Почта", description = "Почта", category = "Работа"),
        AppDomain(id = "3", name = "Телефон", description = "Звонки", category = "Связь"),
        AppDomain(id = "4", name = "Календарь", description = "Планирование", category = "Работа"),
        AppDomain(id = "5", name = "Магазин", description = "Покупки", category = "Покупки"),
        AppDomain(id = "6", name = "Видео", description = "Просмотр", category = "Развлечения"),
        AppDomain(id = "7", name = "Избранное", description = "Заметки", category = "Личное"),
        AppDomain(id = "8", name = "Поиск", description = "Поиск", category = "Инструменты"),
        AppDomain(id = "9", name = "Настройки", description = "Параметры", category = "Система")
    )

    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Витрина",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val startIndex = page * 3
            val endIndex = minOf(startIndex + 3, showcaseApps.size)
            val pageApps = if (startIndex < showcaseApps.size) {
                showcaseApps.subList(startIndex, endIndex)
            } else {
                emptyList()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pageApps.forEach { app ->
                    ShowcaseItem(
                        app = app,
                        onClick = { onAppClick(app) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShowcaseItem(
    app: AppDomain,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = app.name,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = app.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}