package com.example.gamecandor.data

import android.content.Context
import com.example.gamecandor.model.Card
import com.example.gamecandor.model.Category

object CardRepository {
    private const val PREFS = "cards_prefs"
    private const val PLAYED = "played_cards"
    private var cards: List<Card> = emptyList()
    private val categoryDecks = mutableMapOf<Category, MutableList<Card>>()
    fun getQuestionText(context: Context, id: Int): String {
        val name = "q$id"
        val resId =
            context.resources.getIdentifier(
                name,
                "string",
                context.packageName
            )
        return context.getString(resId)
    }

    fun load(context: Context) {
        cards = JsonLoader.loadCards(context)

        categoryDecks.clear()

        Category.entries.forEach { category ->
            categoryDecks[category] =
                cards.filter { it.category == category }
                    .shuffled()
                    .toMutableList()
        }
    }

    fun getCardsByCategory(
        context: Context,
        category: Category
    ): List<Card> {

        val played = loadPlayed(context)

        return cards.filter {
            it.category == category && it.id !in played
        }
    }

    fun markCardPlayed(context: Context, card: Card) {
        val played = loadPlayed(context)
        played.add(card.id)
        savePlayed(context, played)


    }

    fun getRandomCard(): Card {
        return cards.random()
    }

    fun getQuestions(context: Context, card: Card): List<String> {
        return card.questionIds.map {
            getQuestionText(context, it)
        }
    }

    private fun loadPlayed(context: Context): MutableSet<Int> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getStringSet(PLAYED, mutableSetOf())!!
            .map { it.toInt() }
            .toMutableSet()
    }

    private fun savePlayed(context: Context, ids: Set<Int>) {

        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        prefs.edit()
            .putStringSet(
                PLAYED,
                ids.map { it.toString() }.toSet()
            )
            .apply()
    }
}