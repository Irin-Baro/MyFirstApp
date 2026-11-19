package com.example.myapplication

import com.example.myapplication.data.AppRepositoryImpl
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.network.CatalogResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AppRepositoryTest {

    private val mockApiService: ApiService = mock()
    private val repository = AppRepositoryImpl(mockApiService)

    @Test
    fun `getApps should return mapped list when api returns data`() = runTest {
        // 1. Подготовка (Given)
        val mockResponse = listOf(
            CatalogResponse("1", "App 1", "Desc 1", "Cat 1", null),
            CatalogResponse("2", "App 2", "Desc 2", "Cat 2", null)
        )
        whenever(mockApiService.getCatalog()).thenReturn(mockResponse)

        // 2. Действие (When)
        val result = repository.getApps().first()

        // 3. Проверка (Then)
        assertEquals(2, result.size)
        assertEquals("App 1", result[0].name)
        assertEquals("App 2", result[1].name)
    }

    @Test
    fun `getApps should return empty list when api throws exception`() = runTest {
        // 1. Подготовка (Given)
        whenever(mockApiService.getCatalog()).thenThrow(RuntimeException("Network error"))

        // 2. Действие (When)
        val result = repository.getApps().first()

        // 3. Проверка (Then)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getApps should correctly map id from String to String`() = runTest {
        val mockResponse = listOf(
            CatalogResponse("abc-123", "App", "Desc", "Cat", null)
        )
        whenever(mockApiService.getCatalog()).thenReturn(mockResponse)

        val result = repository.getApps().first()

        assertEquals("abc-123", result[0].id)
    }

    @Test
    fun `getApps should preserve all fields after mapping`() = runTest {
        val mockResponse = listOf(
            CatalogResponse("1", "Name", "Description", "Category", null)
        )
        whenever(mockApiService.getCatalog()).thenReturn(mockResponse)

        val result = repository.getApps().first()

        assertEquals("1", result[0].id)
        assertEquals("Name", result[0].name)
        assertEquals("Description", result[0].description)
        assertEquals("Category", result[0].category)
    }

    @Test
    fun `getApps should return empty list when api returns empty list`() = runTest {
        whenever(mockApiService.getCatalog()).thenReturn(emptyList())

        val result = repository.getApps().first()

        assertTrue(result.isEmpty())
    }
}