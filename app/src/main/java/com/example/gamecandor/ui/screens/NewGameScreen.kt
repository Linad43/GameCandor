package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.ui.screens.dialogs.AppTopBar
import com.example.gamecandor.ui.screens.dialogs.GameExistDialog

@Composable
fun NewGameScreen(navController: NavHostController) {
    val context = LocalContext.current
    var gameName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var pendingGameName by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.new_game),
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.create_new_game),
                    style = MaterialTheme.typography.headlineLarge
                )

                OutlinedTextField(
                    value = gameName,
                    onValueChange = { gameName = it },
                    label = { Text(stringResource(R.string.name_game)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(onClick = {
                    if (gameName.isNotBlank()) {
                        if (GameRepository.getGames(context).any { it == gameName }) {
                            pendingGameName = gameName
                            showDialog = true
                        } else {
                            GameRepository.createGame(context, gameName)
                            GameSession.currentGame = gameName
                            navController.navigate(Screens.CHOICE_TYPE_GAME.name) {
                                popUpTo(Screens.MAIN.name) { inclusive = false }
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            R.string.enter_name_game,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.create_and_begin))
                }
            }
            if (showDialog) {
                GameExistDialog(
                    show = showDialog,
                    onRestart = {
                        GameRepository.deleteGame(context, pendingGameName)
                        GameRepository.createGame(context, pendingGameName)

                        GameSession.currentGame = pendingGameName

                        navController.navigate(Screens.CHOICE_TYPE_GAME.name) {
                            popUpTo(Screens.MAIN.name) { inclusive = false }
                        }

                        showDialog = false
                    },
                    onContinue = {
                        GameSession.currentGame = pendingGameName

                        navController.navigate(Screens.CHOICE_TYPE_GAME.name) {
                            popUpTo(Screens.MAIN.name) { inclusive = false }
                        }

                        showDialog = false
                    },
                    onCancel = {
                        showDialog = false
                    }
                )
            }
        }
    )
}
