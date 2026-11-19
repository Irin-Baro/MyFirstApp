package com.example.myapplication

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.AppRepositoryImpl
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.domain.AppDomain

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Создаем ApiService вручную (в реальном проекте его внедряет Hilt)
    val retrofit = remember {
        retrofit2.Retrofit.Builder()
            .baseUrl("http://185.103.109.134/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
    }
    val apiService = remember { retrofit.create(ApiService::class.java) }
    val repository = remember { AppRepositoryImpl(apiService) }

    NavHost(
        navController = navController,
        startDestination = "app_list"
    ) {
        // Экран списка приложений
        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    navController.navigate("app_details/${app.id}")
                }
            )
        }

        // Экран деталей приложения
        composable(
            route = "app_details/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")

            // Состояние для хранения найденного приложения
            var foundApp by remember { mutableStateOf<AppDomain?>(null) }

            // Загружаем приложение по ID
            LaunchedEffect(appId) {
                repository.getApps().collect { apps ->
                    foundApp = apps.find { it.id == appId }
                }
            }

            // Если приложение найдено — показываем экран с настоящими данными
            if (foundApp != null) {
                AppDetailsScreen(
                    app = foundApp!!,
                    onBackClick = { navController.popBackStack() },
                    onShareClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Посмотри это приложение: ${foundApp!!.name}")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Поделиться через…"))
                    },
                    onShowcaseAppClick = { clickedApp ->
                        navController.navigate("app_details/${clickedApp.id}")
                    }
                )
            } else {
                // Пока данные загружаются, показываем экран с текстом "Загрузка..."
                AppDetailsScreen(
                    app = AppDomain(appId ?: "", "Загрузка...", "Подождите...", "Категория"),
                    onBackClick = { navController.popBackStack() },
                    onShareClick = { },
                    onShowcaseAppClick = { }
                )
            }
        }
    }
}