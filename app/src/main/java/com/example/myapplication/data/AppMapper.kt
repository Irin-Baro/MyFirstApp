package com.example.myapplication.data

import com.example.myapplication.domain.AppDomain

// Функция-расширение для превращения DTO в Domain-модель
fun AppDto.toDomain(): AppDomain {
    return AppDomain(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category
    )
}