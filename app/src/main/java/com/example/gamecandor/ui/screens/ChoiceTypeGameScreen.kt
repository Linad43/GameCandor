package com.example.gamecandor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun ChoiceTypeGameScreen(navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Answers List",
                showBack = false,
                onBack = { navController.popBackStack() },
                showMenu = true,
                menuItems = listOf(
                    "Настройки" to { /* действие */ },
                    "Помощь" to { /* действие */ }
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = GameSession.currentGame,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(
                    onClick = { navController.navigate(Screens.CATEGORY.name) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Standard game")
                }
                Button(
                    onClick = { navController.navigate(Screens.RANDOM_CARD.name) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Random card")
                }
            }
        }
    )
}
