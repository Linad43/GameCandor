package com.example.gamecandor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.data.GameRepository
import com.example.gamecandor.ui.screens.GameApp
import com.example.gamecandor.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
//    override fun onPause() {
//        super.onPause()
//        if (GameSession.currentGame.isNotEmpty()) {
//            // Все изменения уже в GameRepository, но для уверенности:
//            GameSession.saveCurrentGame(this)
//        }
//    }

//    override fun onStop() {
//        super.onStop()
//        if (GameSession.currentGame.isNotEmpty()) {
//            // Все изменения уже в GameRepository, но для уверенности:
//            GameSession.saveCurrentGame(this)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        CardRepository.loadCards(this)
        CardRepository.loadQuestions(this)
        GameRepository.load(this)
        val saved = GameRepository.getSaves()
        setContent {
            var textSize by remember { mutableStateOf(saved.textSize) }
            AppTheme(textSize) {

                GameApp(
                    onTextSizeChange = { newSize ->

                        // 🔥 обновляем UI
                        textSize = newSize

                        // 💾 сохраняем
                        val updated = GameRepository.getSaves().copy(
                            textSize = newSize
                        )
                        GameRepository.save(this, updated)
                    }
                )
            }
        }
    }
}
