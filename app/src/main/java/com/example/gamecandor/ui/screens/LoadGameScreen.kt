package com.example.gamecandor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun LoadGameScreen(navController: NavHostController) {
    val context = LocalContext.current
    val games = remember { mutableStateListOf<String>() }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.load_game),
                showBack = true,
                onBack = { navController.popBackStack() },
                showMenu = true,
                menuItems = listOf(
                    stringResource(R.string.settings) to { /* действие */ },
                    stringResource(R.string.help) to { /* действие */ }
                )
            )
        },
        content = { padding ->
            LaunchedEffect(Unit) {
                games.clear()
                games.addAll(GameRepository.getGames(context))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    stringResource(R.string.choice_game),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(games) { game ->
                        Button(onClick = {
                            GameSession.currentGame = game
                            CardRepository.loadCards(context)
                            if (CardRepository.getCards(context).isEmpty()) {
                                navController.navigate(Screens.END_GAME.name)
                            } else {
                                navController.navigate(Screens.CHOICE_TYPE_GAME.name)
                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(game)
                        }
                    }
                }
            }
        }
    )
}