package com.example.gamecandor.data

import android.content.Context
import com.example.gamecandor.model.Card
import com.example.gamecandor.model.Category
import com.example.gamecandor.model.Question
import com.example.gamecandor.model.QuestionWithAnswer

object CardRepository {
    const val TOTAL_CARDS = 88
    private var cards: List<Card> = emptyList()
    private val categoryDecks = mutableMapOf<Category, MutableList<Card>>()
    private var questions: List<Question> = emptyList()

    // Загрузка карт
    fun loadCards(context: Context) {
        cards = JsonLoader.loadCards(context)

        val played = GameRepository.getPlayedCards(context, GameSession.currentGame)
        cards = cards.filter { it.id !in played }

        categoryDecks.clear()

        Category.entries.forEach { category ->
            categoryDecks[category] =
                cards.filter { it.category == category }
                    .shuffled()
                    .toMutableList()
        }
    }

    fun loadQuestions(context: Context) {
        questions = JsonLoader.loadQuestions(context)
    }

    fun getCards(context: Context): List<Card> {
        return cards
    }

    // Получить карты по категории, исключая уже сыгранные для текущей игры
    fun getCardsByCategory(
        context: Context,
        category: Category
    ): List<Card> {

        val played = GameRepository.getPlayedCards(context, GameSession.currentGame)

        return cards.filter { it.category == category && it.id !in played }
    }

    // Отметить карту сыгранной для текущей игры
    fun markCardPlayed(context: Context, card: Card) {
        GameRepository.markCardPlayed(context, GameSession.currentGame, card.id)
        loadCards(context)
    }

    // Получить случайную карту из всех карт
    fun getRandomCard(): Card {
        if(cards.isEmpty()) throw NoMoreCardsException()
        return cards.random()
    }

    // Получить список вопросов карты
    fun getQuestionsInCard(context: Context, card: Card): List<String> {
        return card.questionIds.map {
            getQuestionText(context, it)
        }
    }

    fun getQuestions(): List<Question> {
        return questions
    }

    // Получить текст вопроса по id
    fun getQuestionText(context: Context, id: Int): String {
        return questions[id-1].text
    }

    fun getAllQuestions(context: Context): List<QuestionWithAnswer> {
        val list = mutableListOf<QuestionWithAnswer>()

        for (question in questions) {
            list.add(
                QuestionWithAnswer(
                    id = question.id,
                    question = question.text,
                    answer = null
                )
            )
        }
        return list
    }
}