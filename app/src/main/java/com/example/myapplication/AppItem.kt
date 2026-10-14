package com.example.myapplication

import androidx.compose.ui.graphics.painter.Painter

data class AppItem(
    val icon: Painter,
    val name: String,
    val description: String,
    val category: String
)