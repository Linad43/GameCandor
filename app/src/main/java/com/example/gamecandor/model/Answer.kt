package com.example.gamecandor.model

import android.content.Context
import com.example.gamecandor.data.CardRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Answers(
    val answers: Map<Int, String> = emptyMap()
)

object AnswersRepository {
    private const val FILE_NAME = "answers.json"
    private val json = Json { prettyPrint = true }
    private var save: Answers? = null

    fun load(context: Context): Answers {
        if (save == null) {
            val file = File(context.filesDir, FILE_NAME)
            save = if (file.exists()) {
                val text = file.readText()
                json.decodeFromString<Answers>(text)
            } else {
                Answers()
            }
        }
        return save!!
    }

    fun saveAnswer(context: Context, questionId: Int, answer: String) {
        val currentSave = load(context)
        val updatedAnswers = currentSave.answers.toMutableMap()
        updatedAnswers[questionId] = answer
        save = currentSave.copy(answers = updatedAnswers)
        File(context.filesDir, FILE_NAME).writeText(json.encodeToString(save!!))
    }

    fun getAnswer(context: Context, questionId: Int): String? {
        return load(context).answers[questionId]
    }

    fun getAllQuestionsWithAnswers(context: Context): List<QuestionWithAnswer> {
        val allQuestions = CardRepository.getAllQuestions(context) // должен вернуть List<Question>
        val answers = load(context).answers

        return allQuestions.map { q ->
            QuestionWithAnswer(
                id = q.id,
                question = q.question,
                answer = answers[q.id]
            )
        }
    }

    fun getRandomUnansweredQuestion(context: Context): QuestionWithAnswer? {
        val list = getAllQuestionsWithAnswers(context)
        val unanswered = list.filter { it.answer.isNullOrBlank() }
        return unanswered.randomOrNull()
    }
}