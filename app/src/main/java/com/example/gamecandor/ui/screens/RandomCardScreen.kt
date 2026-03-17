package com.example.gamecandor.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.model.Card
import com.example.gamecandor.ui.components.SwipeableCard

@Composable
fun RandomCardScreen(navController: NavHostController) {
    val context = LocalContext.current
    CardRepository.load(context)
    if (CardRepository.getCards(context).isEmpty()) {
        navController.navigate("end_game")
        return
    }
    var card by remember { mutableStateOf<Card>(CardRepository.getRandomCard()) }

    DisposableEffect(Unit) {
        onDispose {
            // Автосохранение текущей игры при выходе с экрана
            card.let {
                GameRepository.markCardPlayed(context, GameSession.currentGame, it.id)
            }
        }
    }
    AnimatedContent(
        targetState = card,
        label = "card_animation",
        transitionSpec = {
            scaleIn(initialScale = 0.8f) + scaleIn(initialScale = 1.5f) + scaleIn(initialScale = 1f) + fadeIn() togetherWith
                    scaleOut(targetScale = 0.8f) + scaleOut(targetScale = 1.2f) + scaleOut(
                targetScale = 1f
            ) + fadeOut()
        }
    ) { targetCard ->
        SwipeableCard(
            card = targetCard,
            onPlayed = {
                CardRepository.markCardPlayed(context, targetCard)
                card = CardRepository.getRandomCard()
            },
            onCancel = {
                card = CardRepository.getRandomCard()
            }
        )
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Случайная карта", style = MaterialTheme.typography.headlineLarge)
//
//        card?.let {
//            Text("Карточка №${it.id}")
//            val questions = CardRepository.getQuestions(context, it)
//            questions.forEach { q -> Text(q) }
//        }
//
//        Button(onClick = {
//            // Автосохранение и новая карта
//            card?.let {
//                GameRepository.markCardPlayed(context, GameSession.currentGame, it.id)
//            }
//            card = CardRepository.getRandomCard()
//        }, modifier = Modifier.fillMaxWidth()) {
//            Text("Следующая карта")
//        }
//    }
}