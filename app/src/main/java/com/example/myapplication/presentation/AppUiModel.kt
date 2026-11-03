package com.example.myapplication.presentation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.domain.AppDomain

data class AppUiModel(
    val domain: AppDomain,
    val icon: ImageVector
)