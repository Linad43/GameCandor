package com.example.gamecandor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.data.GameSession
import com.example.gamecandor.ui.screens.GameApp
import com.example.gamecandor.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onPause() {
        super.onPause()
        if (GameSession.currentGame.isNotEmpty()) {
            // Все изменения уже в GameRepository, но для уверенности:
            GameSession.saveCurrentGame(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (GameSession.currentGame.isNotEmpty()) {
            // Все изменения уже в GameRepository, но для уверенности:
            GameSession.saveCurrentGame(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CardRepository.load(this)
        GameRepository.load(this)
        setContent {
            GameApp()
//            MainScreen(gameApp.)
//            var selectedCategory by remember {
//                mutableStateOf<Category?>(null)
//            }
//
//            if (selectedCategory == null) {
//
//                CategoryScreen(
//                    onCategorySelected = { category ->
//                        selectedCategory = category
//                    }
//                )
//
//            } else {
//
//                CategoryCardsScreen(
//                    category = selectedCategory!!
//                )
//
//            }
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
