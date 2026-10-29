package com.example.myapplication

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.AppRepositoryImpl

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Создаём репозиторий, чтобы достать данные для экрана деталей
    val repository = AppRepositoryImpl()

    NavHost(
        navController = navController,
        startDestination = "app_list"
    ) {
        // Экран списка приложений
        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    // Переходим по ID приложения
                    navController.navigate("app_details/${app.id}")
                }
            )
        }

        // Экран деталей приложения
        composable(
            route = "app_details/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt("appId")

            // Ищем приложение в репозитории по ID
            val app = repository.getApps().find { it.id == appId }

            if (app != null) {
                AppDetailsScreen(
                    app = app,
                    onBackClick = { navController.popBackStack() },
                    onShareClick = {
                        // Логика кнопки «Поделиться» (возвращаем из первого задания)
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "Посмотри это приложение: ${app.name}")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Поделиться через…"))
                    },
                    onInstallClick = { },
                    onShowcaseAppClick = { clickedApp ->
                        // При клике в витрине переходим на экран деталей другого приложения
                        navController.navigate("app_details/${clickedApp.id}")
                    }
                )
            }
        }
    }
}