package com.example.gamecandor.data

import android.content.Context
import com.example.gamecandor.model.Card
import com.example.gamecandor.model.Category

//
//object CardRepository {
//    private const val PREFS = "cards_prefs"
//    private const val PLAYED = "played_cards"
//    private var cards: List<Card> = emptyList()
//    private val categoryDecks = mutableMapOf<Category, MutableList<Card>>()
//    fun getQuestionText(context: Context, id: Int): String {
//        val name = "q$id"
//        val resId =
//            context.resources.getIdentifier(
//                name,
//                "string",
//                context.packageName
//            )
//        return context.getString(resId)
//    }
//
//    fun load(context: Context) {
//        cards = JsonLoader.loadCards(context)
//
//        categoryDecks.clear()
//
//        Category.entries.forEach { category ->
//            categoryDecks[category] =
//                cards.filter { it.category == category }
//                    .shuffled()
//                    .toMutableList()
//        }
//    }
//
//    fun getCardsByCategory(
//        context: Context,
//        category: Category
//    ): List<Card> {
//
//        val played = loadPlayed(context)
//
//        return cards.filter {
//            it.category == category && it.id !in played
//        }
//    }
//
//    fun markCardPlayed(context: Context, card: Card) {
//        val played = loadPlayed(context)
//        played.add(card.id)
//        savePlayed(context, played)
//
//
//    }
//
//    fun getRandomCard(): Card {
//        return cards.random()
//    }
//
//    fun getQuestions(context: Context, card: Card): List<String> {
//        return card.questionIds.map {
//            getQuestionText(context, it)
//        }
//    }
//
//    private fun loadPlayed(context: Context): MutableSet<Int> {
//        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
//        return prefs.getStringSet(PLAYED, mutableSetOf())!!
//            .map { it.toInt() }
//            .toMutableSet()
//    }
//
//    private fun savePlayed(context: Context, ids: Set<Int>) {
//
//        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
//
//        prefs.edit()
//            .putStringSet(
//                PLAYED,
//                ids.map { it.toString() }.toSet()
//            )
//            .apply()
//    }
//}
object CardRepository {

    private var cards: List<Card> = emptyList()
    private val categoryDecks = mutableMapOf<Category, MutableList<Card>>()

    // Загрузка карт
    fun load(context: Context) {
        cards = JsonLoader.loadCards(context)

        val played = GameRepository.getPlayedCards(context, GameSession.currentGame)
        cards = cards.filter { it.id !in played }
//        if(cards.isEmpty())
//            navController.navigate("choice_type_game")


        categoryDecks.clear()

        Category.entries.forEach { category ->
            categoryDecks[category] =
                cards.filter { it.category == category }
                    .shuffled()
                    .toMutableList()
        }
    }

    fun getCards(context: Context):List<Card>{
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
        load(context)
    }

    // Получить случайную карту из всех карт
    fun getRandomCard(): Card {
        return cards.random()
    }

    // Получить список вопросов карты
    fun getQuestions(context: Context, card: Card): List<String> {
        return card.questionIds.map {
            getQuestionText(context, it)
        }
    }

    // Получить текст вопроса по id
    fun getQuestionText(context: Context, id: Int): String {
        val name = "q$id"
        val resId = context.resources.getIdentifier(name, "string", context.packageName)
        return context.getString(resId)
    }
}