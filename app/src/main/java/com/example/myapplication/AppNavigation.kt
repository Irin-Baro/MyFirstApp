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
            arguments = listOf(navArgument("appId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt("appId")

            // В реальном проекте здесь должна быть AppDetailsViewModel,
            // которая берёт приложение из репозитория через Hilt.
            // Чтобы не усложнять, мы просто передадим ID, а внутри экрана
            // возьмём данные из общего списка (если он уже загружен).
            // Но чтобы не было краша, давайте просто передадим заглушку.

            // ВНИМАНИЕ: Если у вас нет AppDetailsViewModel, мы сделаем временную заглушку,
            // чтобы приложение запустилось.
            val dummyApp = com.example.myapplication.domain.AppDomain(
                id = appId ?: 0,
                name = "Загрузка...",
                description = "",
                category = ""
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