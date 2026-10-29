package com.example.myapplication.data

import androidx.compose.ui.graphics.vector.ImageVector

data class AppDto(
    val id: Int,
    val icon: ImageVector,
    val name: String,
    val description: String,
    val category: String
)