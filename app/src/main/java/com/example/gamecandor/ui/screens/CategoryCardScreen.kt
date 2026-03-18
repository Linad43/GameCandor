package com.example.gamecandor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Card
import com.example.gamecandor.model.Category
import com.example.gamecandor.ui.components.CardItem
import com.example.gamecandor.ui.components.SwipeableCard
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun CategoryCardsScreen(navController: NavHostController, category: Category) {
    val context = LocalContext.current
    var selectedCard by remember {
        mutableStateOf<Card?>(null)
    }

    val cards = CardRepository.getCardsByCategory(context, category)

    if (selectedCard == null) {
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cards) { card ->
                        CardItem(
                            card = card,
                            onOpen = { clickedCard ->
                                selectedCard = clickedCard
                            }
                        )
                    }
                }
            }
        )
    } else {
        SwipeableCard(
            card = selectedCard!!,
            onPlayed = {
                CardRepository.markCardPlayed(context, selectedCard!!)
                selectedCard = null
            },
            onCancel = {
                selectedCard = null
            }
        )
    }
}