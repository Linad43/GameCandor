package com.example.gamecandor.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun EndGameScreen(navController: NavHostController) {
    // Перехват системной кнопки "Назад"
    BackHandler {
        // Вместо стандартного поведения — делаем своё
        navController.navigate(Screens.MAIN.name) {
            popUpTo(0) { inclusive = true } // очистить весь стек
            launchSingleTop = true
        }
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.finished_game),
                showBack = true,
                onBack = {
                    navController.navigate(Screens.MAIN.name) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                showMenu = false,
                menuItems = listOf()
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.finished_game),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
    )
}