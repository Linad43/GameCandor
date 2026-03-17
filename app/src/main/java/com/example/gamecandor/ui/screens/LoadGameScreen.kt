package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.ui.components.CardContent

@Composable
fun LoadGameScreen(navController: NavHostController) {
    val context = LocalContext.current
    val games = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        games.clear()
        games.addAll(GameRepository.getGames(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Выберите игру",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(games) { game ->
                Button(onClick = {
                    GameSession.currentGame = game
                    CardRepository.load(context)
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