package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.Category

@Composable
fun CategoryScreen(onCategorySelected: (Category) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
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
}