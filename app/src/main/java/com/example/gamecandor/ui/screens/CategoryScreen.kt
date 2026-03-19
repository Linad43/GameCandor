package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Category
import com.example.gamecandor.ui.components.CategoryCard
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun CategoryScreen(
    navController: NavHostController,
    onCategorySelected: (Category) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.choice_category),
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
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Category.entries.forEach { category ->
                    CategoryCard(
                        category = category,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (CardRepository.getCardsByCategory(context, category).isEmpty()) {
                                Toast.makeText(
                                    context,
                                    R.string.finished_game,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                onCategorySelected(category)
                            }
                        }
                    )
                }
            }
        })
}