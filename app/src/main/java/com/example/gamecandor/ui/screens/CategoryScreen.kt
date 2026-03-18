package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Category
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun CategoryScreen(navController: NavHostController, onCategorySelected: (Category) -> Unit) {
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
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Category.entries.forEach { category ->

                    Button(
                        onClick = {
                            if (CardRepository.getCardsByCategory(context, category).isEmpty()) {
                                Toast.makeText(context, "All done", Toast.LENGTH_SHORT).show()
                            } else {
                                onCategorySelected(category)
                            }
                        }
                    ) {
                        Text(category.name)
                    }
                }
            }
        })
}