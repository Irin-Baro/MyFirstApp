package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.example.myapplication.data.AppDto
import com.example.myapplication.data.toDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class AppMapperTest {

    @Test
    fun `toDomain should map all fields correctly`() {
        // 1. Подготовка (Given)
        val dto = AppDto(
            id = 1,
            icon = Icons.Filled.Home,
            name = "Test App",
            description = "Test Description",
            category = "Test Category"
        )

        // 2. Действие (When)
        val domain = dto.toDomain()

        // 3. Проверка (Then)
        assertEquals("1", domain.id)
        assertEquals("Test App", domain.name)
        assertEquals("Test Description", domain.description)
        assertEquals("Test Category", domain.category)
    }

    @Test
    fun `toDomain should handle empty strings`() {
        val dto = AppDto(
            id = 2,
            icon = Icons.Filled.Home,
            name = "",
            description = "",
            category = ""
        )
        val domain = dto.toDomain()

        assertEquals("2", domain.id)
        assertEquals("", domain.name)
        assertEquals("", domain.description)
        assertEquals("", domain.category)
    }

    @Test
    fun `toDomain should handle special characters`() {
        val dto = AppDto(
            id = 3,
            icon = Icons.Filled.Home,
            name = "App & Co.",
            description = "100% free!",
            category = "Games / Action"
        )
        val domain = dto.toDomain()

        assertEquals("App & Co.", domain.name)
        assertEquals("100% free!", domain.description)
        assertEquals("Games / Action", domain.category)
    }

    @Test
    fun `toDomain should handle long strings`() {
        val longText = "A".repeat(1000)
        val dto = AppDto(
            id = 4,
            icon = Icons.Filled.Home,
            name = longText,
            description = longText,
            category = longText
        )
        val domain = dto.toDomain()

        assertEquals(longText, domain.name)
        assertEquals(longText, domain.description)
        assertEquals(longText, domain.category)
    }

    @Test
    fun `toDomain should convert id to string`() {
        val dto = AppDto(
            id = 12345,
            icon = Icons.Filled.Home,
            name = "Test",
            description = "",
            category = ""
        )
        val domain = dto.toDomain()

        assertEquals("12345", domain.id)
    }
}