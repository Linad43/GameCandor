package com.example.gamecandor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.JsonLoader
import com.example.gamecandor.model.Category
import com.example.gamecandor.ui.components.CardView
import com.example.gamecandor.ui.screens.CategoryCardsScreen
import com.example.gamecandor.ui.screens.CategoryScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CardRepository.load(this)

        setContent {
            var selectedCategory by remember {
                mutableStateOf<Category?>(null)
            }

            if (selectedCategory == null) {

                CategoryScreen(
                    onCategorySelected = { category ->
                        selectedCategory = category
                    }
                )

            } else {

                CategoryCardsScreen(
                    category = selectedCategory!!
                )

            }
//            GameScreen()
        }
    }
}

//@Composable
//fun GameScreen() {
//    val context = LocalContext.current
//    var currentCard by rememberSaveable {
//        mutableStateOf(
//            CardRepository.getRandomCard()
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(20.dp))
//
//        currentCard?.let {
//            CardView(it)
//        }
//
//        Button(
//            onClick = {
//                currentCard = JsonLoader.loadCards(context).random()
//            }
//        ) {
//            Text("New card")
//        }
//    }
//}