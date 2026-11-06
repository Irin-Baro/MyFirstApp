package com.example.myapplication

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

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

            // Но так как мы не передали apiService вручную, лучше использовать заглушку
            // В следующем задании мы сделаем AppDetailsViewModel, а пока просто покажем ID
            val dummyApp = com.example.myapplication.domain.AppDomain(
                id = appId ?: "",
                name = "Приложение $appId", // Показываем ID вместо "Загрузка..."
                description = "Подробная информация появится в следующем задании",
                category = "Категория"
            )

            AppDetailsScreen(
                app = dummyApp,
                onBackClick = { navController.popBackStack() },
                onShareClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Посмотри это приложение!")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Поделиться через…"))
                },
                onInstallClick = { },
                onShowcaseAppClick = { clickedApp ->
                    navController.navigate("app_details/${clickedApp.id}")
                }
            )
        }
    }
}