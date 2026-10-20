package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    // Создаем контроллер навигации
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "app_list" // Стартовый экран - список приложений
    ) {
        // Экран списка приложений
        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    // При клике на приложение переходим на экран деталей
                    // Передаем имя приложения, чтобы потом найти его в списке
                    navController.navigate("app_details/${app.name}")
                }
            )
        }

        // Экран деталей приложения
        composable(
            route = "app_details/{appName}",
            arguments = listOf(navArgument("appName") { type = NavType.StringType })
        ) { backStackEntry ->
            // Получаем имя приложения из аргументов навигации
            val appName = backStackEntry.arguments?.getString("appName")
            // Ищем это приложение в нашем списке
            val app = sampleApps.find { it.name == appName }

            if (app != null) {
                AppDetailsScreen(
                    app = app,
                    onBackClick = { navController.popBackStack() },
                    onShareClick = { },
                    onInstallClick = { },
                    onShowcaseAppClick = { clickedApp ->
                        // При клике в витрине переходим на экран деталей другого приложения
                        navController.navigate("app_details/${clickedApp.name}")
                    }
                )
            }
        }
    }
}