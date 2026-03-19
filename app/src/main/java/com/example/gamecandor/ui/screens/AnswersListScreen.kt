package com.example.gamecandor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamecandor.R
import com.example.gamecandor.model.AnswersRepository
import com.example.gamecandor.ui.components.QuestionItem
import com.example.gamecandor.ui.screens.dialogs.AppTopBar


@Composable
fun AnswersListScreen(navController: NavHostController) {
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }

    val questions = remember {
        AnswersRepository.getAllQuestionsWithAnswers(context)
    }

    val filteredList = remember(searchQuery, questions) {
        if (searchQuery.isBlank()) {
            questions
        } else {
            questions.filter { item ->
                item.question.contains(
                    searchQuery.trim(),
                    ignoreCase = true
                )
            }
        }
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.single_game),
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
                    .padding(padding)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            stringResource(R.string.found_question),
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    singleLine = true
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(questions) { item ->
                        QuestionItem(
                            item,
                            onClick = {
                                navController.navigate("${Screens.SINGLE.name}/${item.id}")
                            })
                    }
                }

                Button(
                    onClick = {
                        val q = AnswersRepository.getRandomUnansweredQuestion(context)
                        if (q != null) {
                            navController.navigate("${Screens.SINGLE.name}/${q.id}")
                        } else {
                            Toast.makeText(context, R.string.finished_game, Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.random_question))
                }
            }
        }
    )
}
