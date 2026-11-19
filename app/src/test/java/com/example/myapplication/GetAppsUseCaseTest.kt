package com.example.myapplication

import com.example.myapplication.data.AppRepository
import com.example.myapplication.domain.AppDomain
import com.example.myapplication.domain.GetAppsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAppsUseCaseTest {

    private val mockRepository: AppRepository = mock()
    private val useCase = GetAppsUseCase(mockRepository)

    @Test
    fun `invoke should return list of apps from repository`() = runTest {
        val mockApps = listOf(
            AppDomain("1", "App 1", "Desc 1", "Cat 1"),
            AppDomain("2", "App 2", "Desc 2", "Cat 2")
        )
        whenever(mockRepository.getApps()).thenReturn(flowOf(mockApps))

        val result = useCase().first()

        assertEquals(2, result.size)
        assertEquals("App 1", result[0].name)
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        whenever(mockRepository.getApps()).thenReturn(flowOf(emptyList()))

        val result = useCase().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke should propagate exception from repository`() = runTest {
        whenever(mockRepository.getApps()).thenReturn(
            kotlinx.coroutines.flow.flow { throw RuntimeException("Error") }
        )

        try {
            useCase().first()
        } catch (e: Exception) {
            assertEquals("Error", e.message)
        }
    }

    @Test
    fun `invoke should preserve data order`() = runTest {
        val mockApps = listOf(
            AppDomain("1", "First", "", ""),
            AppDomain("2", "Second", "", "")
        )
        whenever(mockRepository.getApps()).thenReturn(flowOf(mockApps))

        val result = useCase().first()

        assertEquals("First", result[0].name)
        assertEquals("Second", result[1].name)
    }

    @Test
    fun `invoke should return exactly the same objects as repository`() = runTest {
        val app = AppDomain("1", "Test", "Desc", "Cat")
        whenever(mockRepository.getApps()).thenReturn(flowOf(listOf(app)))

        val result = useCase().first()

        assertEquals(app, result[0])
    }
}