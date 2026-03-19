package com.example.gamecandor.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.model.Card
import com.example.gamecandor.ui.components.SwipeableCard
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun RandomCardScreen(navController: NavHostController) {
    val context = LocalContext.current
    CardRepository.loadCards(context)
    if (CardRepository.getCards(context).isEmpty()) {
        navController.navigate(Screens.END_GAME.name)
        return
    }
    var card by remember { mutableStateOf<Card>(CardRepository.getRandomCard()) }
    var endGame by remember { mutableStateOf(false) }
    if (endGame) {
        navController.navigate(Screens.END_GAME.name) {
            popUpTo(Screens.MAIN.name) { inclusive = false }
        }
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.random_card),
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
            DisposableEffect(Unit) {
                onDispose {
                    // Автосохранение текущей игры при выходе с экрана
                    card.let {
                        GameRepository.markCardPlayed(context, GameSession.currentGame, it.id)
                    }
                }
            }
            AnimatedContent(
                modifier = Modifier.padding(padding),
                targetState = card,
                label = "card_animation",
                transitionSpec = {
                    scaleIn(initialScale = 0.8f) + scaleIn(initialScale = 1.5f) + scaleIn(
                        initialScale = 1f
                    ) + fadeIn() togetherWith
                            scaleOut(targetScale = 0.8f) + scaleOut(targetScale = 1.2f) + scaleOut(
                        targetScale = 1f
                    ) + fadeOut()
                }
            ) { targetCard ->
                SwipeableCard(
                    card = targetCard,
                    onPlayed = {
                        CardRepository.markCardPlayed(context, targetCard)
                        try {
                            card = CardRepository.getRandomCard()
                        } catch (e: Exception) {
                            endGame = true
                        }

                    },
                    onCancel = {
                        try {
                            card = CardRepository.getRandomCard()
                        } catch (e: Exception) {
                            endGame = true
                        }
                    }
                )
            }
        }
    )
}