package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.data.CardRepository
import com.example.gamecandor.model.AnswersRepository
import com.example.gamecandor.ui.components.QuestionText
import com.example.gamecandor.ui.screens.dialogs.AppTopBar

@Composable
fun SinglePlayerCard(
    navController: NavHostController,
    questionId: Int
) {
    val context = LocalContext.current

    val question = remember {
        CardRepository.getQuestions()[questionId]
    }

    val cards = AnswersRepository.getAllQuestionsWithAnswers(context)
    var answerText by remember {
        mutableStateOf(
            AnswersRepository.getAnswer(context, questionId) ?: ""
        )
    }
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
                    .padding(16.dp)
//            .verticalScroll(scrollState)
            ) {
                // Карточка с вопросом и фоном
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
//                .height(300.dp)
                        .weight(3f),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                ) {
                    Box {
                        Image(
                            painter = painterResource(question.category.background),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        QuestionText(
                            question.text,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Многострочный ввод ответа
                OutlinedTextField(
                    value = answerText,
                    onValueChange = { answerText = it },
                    label = { Text("Ваш ответ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    maxLines = 10
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        AnswersRepository.saveAnswer(context, questionId, answerText)
                        Toast.makeText(context, "Ответ сохранён", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Сохранить ответ")
                }
            }
        }
    )
}