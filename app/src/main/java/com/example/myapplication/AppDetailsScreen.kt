package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.AppDomain
import com.example.myapplication.presentation.AppDetailsState
import com.example.myapplication.presentation.AppDetailsViewModel
import com.example.myapplication.presentation.InstallState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    app: AppDomain,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onShowcaseAppClick: (AppDomain) -> Unit,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // КЛЮЧЕВАЯ СТРОКА: передаем данные приложения в ViewModel
    LaunchedEffect(app) {
        viewModel.initContent(app)
    }

    // Определяем текущее состояние кнопки
    val currentInstallState = (state as? AppDetailsState.Content)?.installState ?: InstallState.NotInstalled

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(app.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Поделиться",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = app.name,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = app.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = app.category,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            InstallButton(
                installState = currentInstallState,
                onInstallClick = { viewModel.onInstallClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Скриншоты",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { index ->
                    Card(
                        modifier = Modifier.size(120.dp, 80.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = "Скриншот ${index + 1}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Описание приложения",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = app.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Разработчик",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "VK Education",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            ShowcasePager(
                onAppClick = onShowcaseAppClick
            )
        }
    }
}

@Composable
fun InstallButton(
    installState: InstallState,
    onInstallClick: () -> Unit
) {
    when (installState) {
        is InstallState.NotInstalled -> {
            Button(
                onClick = onInstallClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Установить")
            }
        }
        is InstallState.Installing -> {
            Button(
                onClick = onInstallClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Отменить (${installState.progress}%)")
            }
        }
        is InstallState.Installed -> {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Text("Установлено")
            }
        }
    }
}